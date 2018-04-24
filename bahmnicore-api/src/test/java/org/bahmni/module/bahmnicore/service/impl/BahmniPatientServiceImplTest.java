package org.bahmni.module.bahmnicore.service.impl;

import org.bahmni.module.bahmnicore.contract.patient.PatientSearchParameters;
import org.bahmni.module.bahmnicore.contract.patient.mapper.PatientResponseMapper;
import org.bahmni.module.bahmnicore.contract.patient.response.PatientConfigResponse;
import org.bahmni.module.bahmnicore.contract.patient.response.PatientResponse;
import org.bahmni.module.bahmnicore.dao.PatientDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openmrs.Concept;
import org.openmrs.Person;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PersonService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BahmniPatientServiceImplTest {
    @Mock
    private PersonService personService;
    @Mock
    private ConceptService conceptService;
    @Mock
    private PatientDao patientDao;
    @Mock
    private PatientSearchParameters searchParameters;
    @Mock
    private PatientResponseMapper patientResponseMapper;

    private BahmniPatientServiceImpl bahmniPatientService;

    @Before
    public void setup() {
        initMocks(this);
        bahmniPatientService = new BahmniPatientServiceImpl(personService, conceptService, patientDao);
    }

    @Test
    public void shouldGetPatientConfig() throws Exception {
        List<PersonAttributeType> personAttributeTypes = new ArrayList<>();
        personAttributeTypes.add(new PersonAttributeType() {{
            this.setName("class");
            this.setDescription("Class");
            this.setFormat("org.openmrs.Concept");
            this.setSortWeight(10.0);
            this.setForeignKey(10);
        }});
        personAttributeTypes.add(new PersonAttributeType() {{
            this.setName("primaryContact");
            this.setDescription("Primary Contact");
            this.setFormat("java.lang.String");
            this.setSortWeight(10.0);
        }});

        when(personService.getAllPersonAttributeTypes()).thenReturn(personAttributeTypes);
        when(conceptService.getConcept(anyInt())).thenReturn(new Concept());

        PatientConfigResponse config = bahmniPatientService.getConfig();
        assertEquals(2, config.getPersonAttributeTypes().size());
        assertEquals("class", config.getPersonAttributeTypes().get(0).getName());
        assertEquals("primaryContact", config.getPersonAttributeTypes().get(1).getName());
    }

    @Test
    public void shouldGetPatientByPartialIdentifier() throws Exception {
        boolean shouldMatchExactPatientId = false;
        bahmniPatientService.get("partial_identifier", shouldMatchExactPatientId);
        verify(patientDao).getPatients("partial_identifier", shouldMatchExactPatientId);
    }

    @Test
    public void shouldGetSimiliarPatientResponseFromPersonSet() throws Exception {
        Set<Person> persons = new HashSet<Person>();
        persons.add(new Person());
        persons.add(new Person());
        when(personService.getSimilarPeople(searchParameters.getName(), null, searchParameters.getGender())).thenReturn(persons);

        List<PatientResponse> response = bahmniPatientService.searchSimilarPatients(searchParameters, patientResponseMapper);

        assertEquals(response.size(), 2);

    }
}
