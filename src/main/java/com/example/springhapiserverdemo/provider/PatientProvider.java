package com.example.springhapiserverdemo.provider;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class PatientProvider implements IResourceProvider {
    /*@Autowired
    private FhirContext ctx;*/

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Patient.class;
    }

    @Read()
    public Patient read(@IdParam IdType theId) {
        Patient patient = new Patient();
        // Id
        patient.setId("example");

        // Narrative
        Narrative narrative = patient.getText();
        narrative.setStatus(Narrative.NarrativeStatus.GENERATED);
        narrative.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">\n\t\t\t<table>\n\t\t\t\t<tbody>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td>Name</td>\n\t\t\t\t\t\t<td>Peter James \n              <b>Chalmers</b> (&quot;Jim&quot;)\n            </td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td>Address</td>\n\t\t\t\t\t\t<td>534 Erewhon, Pleasantville, Vic, 3999</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td>Contacts</td>\n\t\t\t\t\t\t<td>Home: unknown. Work: (03) 5555 6473</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td>Id</td>\n\t\t\t\t\t\t<td>MRN: 12345 (Acme Healthcare)</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</tbody>\n\t\t\t</table>\n\t\t</div>");

        // Identifier
        Identifier identifier = patient.addIdentifier();
        identifier.setUse(Identifier.IdentifierUse.USUAL);
        CodeableConcept codeableConcept = identifier.getType();
        List<Coding> codings = codeableConcept.getCoding();
        codings.add(new Coding().setSystem("http://terminology.hl7.org/CodeSystem/v2-0203").setCode("MR"));
        identifier.setSystem("urn:oid:1.2.36.146.595.217.0.1");
        identifier.setValue("12345");
        Period period = identifier.getPeriod();
        period.setStart(new Date());

        Reference assigner = identifier.getAssigner();
        assigner.setDisplay("Acme Healthcare");

        patient.setActive(true);

        // Name
        HumanName name1 = patient.addName();
        name1.setUse(HumanName.NameUse.OFFICIAL);
        name1.setFamily("Smith");
        StringType firstName1 = name1.addGivenElement();
        firstName1.setValue("Peter");
        StringType secondName1 = name1.addGivenElement();
        secondName1.setValue("James");

        HumanName name2 = patient.addName();
        name2.setUse(HumanName.NameUse.USUAL);
        StringType firstName2 = name2.addGivenElement();
        firstName2.setValue("Jim");

        HumanName name3 = patient.addName();
        name3.setUse(HumanName.NameUse.MAIDEN);
        name3.setFamily("Windsor");
        StringType firstName3 = name3.addGivenElement();
        firstName3.setValue("Peter");
        StringType secondName3 = name3.addGivenElement();
        secondName3.setValue("James");
        Period namePeriod = name3.getPeriod();
        namePeriod.setEnd(new Date());

        List<ContactPoint> telecoms = patient.getTelecom();
        telecoms.add(new ContactPoint().setUse(ContactPoint.ContactPointUse.HOME));
        telecoms.add(new ContactPoint().setUse(ContactPoint.ContactPointUse.WORK)
                .setSystem(ContactPoint.ContactPointSystem.PHONE)
                .setValue("(03) 5555 6473")
                .setRank(1));

        telecoms.add(new ContactPoint().setUse(ContactPoint.ContactPointUse.MOBILE)
                .setSystem(ContactPoint.ContactPointSystem.PHONE)
                .setValue("(03) 3410 5613")
                .setRank(2));

        telecoms.add(new ContactPoint().setUse(ContactPoint.ContactPointUse.OLD)
                .setSystem(ContactPoint.ContactPointSystem.PHONE)
                .setValue("(03) 5555 8834")
                .setPeriod(new Period().setEnd(new Date())));

        patient.setGender(Enumerations.AdministrativeGender.MALE);
        patient.setBirthDate(new Date());
        DateType birthDate = patient.getBirthDateElement();
        birthDate.setValue(new Date());
        birthDate.setId("birthDate Id");
        birthDate.addExtension(new Extension("http://hl7.org/fhir/StructureDefinition/patient-birthTime", new StringType("1974-12-25T14:35:45-05:00")));
        // Birth Date Extension

        patient.setDeceased(new BooleanType(false));

        // Address
        List<Address> addresses = patient.getAddress();
        addresses.add(new Address().setUse(Address.AddressUse.HOME)
                .setType(Address.AddressType.BOTH)
                .setText("534 Erewhon St PeasantVille, Rainbow, Vic  3999")
                .setLine(Arrays.asList(new StringType("534 Erewhon St")))
                .setCity("PleasantVille")
                .setDistrict("Rainbow").setState("Vic").setPostalCode("3999").setPeriod(new Period().setStart(new Date())));

        // Contact

        Meta meta = patient.getMeta();
        meta.setLastUpdated(new Date());

        FhirContext fhirContext = FhirContext.forR4();
        IParser parser = fhirContext.newJsonParser();
        parser.setPrettyPrint(true);

        System.out.println(parser.encodeResourceToString(patient));

        return patient;
    }
}
