#+TITLE: Referat - Gruppemøte

Sted: Zoom-møte

Dato: 8. februar 2021

Starttid på første sak: kl. 15:30

Referent: Sigurd Johnsen Setså

Tilstede:
- Heming
- Sigurd
- Julian
- Anders

* Sak I: Intern kommunikasjon

Kontaktinformasjon:

| Navn      | Lagrolle           | Telefonnummer | E-post                       |
|-----------+--------------------+---------------+------------------------------|
| Sigurd    | Teamleder          |      46809385 | sigurdsets@gmail.com         |
| Heming    | Arkitekt           |      90898953 | heming.hanevik@gmail.com     |
| Julian    | Arkitekt           |      99250615 | julian.s.magnussen@gmail.com |
| Anders    | Kodekvalitet       |      95483322 | jakeowens@protonmail.com     |
| Sebastian | Nettverksansvarlig |      92653403 | sebastian.kvamme@gmail.com   |

Discord som hovedkommunikasjon med telefon i nød eller når det er
kritisk å få tak i noen.  Være flinke på å oppdatere project-board.

* Sak II: Prosess

Vi ble enige om å bruke Kanban-metoden for å strukturere prosessen.
Vi var alle enige om at det var en lettere måte å få gjort arbeidet
på.  Daglig møte med voice eller «Online-tid».

* Sak III: Oversikt over produkt

Vi ble enige om å bruke Kanban-metoden for å strukturere prosessen.
Vi var alle enige om at det var en lettere måte å få gjort arbeidet
på.  Daglig møte med voice eller «Online-tid».

* Spesifikasjon av forventet produkt:
- Vi ønsker å lage en digital versjon av brettspillet RoboRally (eldre
  versjon) som kan spilles sammen på flere enheter.

** Som spiller ønsker jeg å se spillebrettet for å kunne spille spillet.

_Akseptansekriterier_:

- Vise et komplett brett.

_Arbeidsoppgaver_:

- Lage GUI.

** som spiller ønsker jeg å se hvor roboten min er på brettet for å kunne vite hvor brikken er.

_Akseptansekriterier_:

- Vise spillerens robot på spillebrettet.

_Arbeidsoppgaver_:

- Opprette grafikk for spiller.

** som utvikler ønsker jeg å kunne flytte posisjonen til en brikke for å kunne teste «victory-condition».

_Akseptansekriterier_:

- Robot beveger seg i relasjon til input fra tastatur.

_Arbeidsoppgaver_:

- Implementere input funksjon fra spiller.

** som spiller ønsker jeg å kunne flytte brikken min til flagget for å kunne vinne spillet.

_Akseptansekriterier_:

- Brikken og flagget kan okkupere samme felt på spillebrettet.
- Spilleren kan flytte på brikken sin.
- Det må være et flagg på brettet.
- Spiller får vite at de har vunnet dersom de flytter brikken sin gjennom alle flaggene på brettet i riktig rekkefølge.

_Arbeidsoppgaver_:

- Vise flagg.
- Implementere kode for «win-condition».
- Implementere funksjonalitet for å sjekke posisjon til spiller.
- Feilhåndtering av «win-condition».

** Som spiller ønsker jeg å spille spillet med andre over internett.

_Akseptansekriterier_:

- Flere kan spille samtidig over internett.

_Arbeidsoppgaver_:

- Implementere multiplayer.

** Som spiller ønsker jeg å få utdelt kort for å vite hvilken kort jeg har.

_Akseptansekriterier_:

- Spiller får utdelt kort.
- Kort vises for spiller.
- Kortene må vise informasjon om hvordan roboten skal flyttes.

_Arbeidsoppgaver_:
- Implementere funksjonalitet for å gi spiller kort.
- Vise kort.

** Som spiller ønsker jeg å velge kort fra de jeg fikk utdelt til å programmere roboten for å kunne bevege roboten.

_Akseptansekriterier_:
- Kortene må kunnes plasseres i en gitt rekkefølge for å programmere roboten.
- Spilleren skal kunne velge opp til 5 kort.

_Arbeidsoppgaver_:
- Implementere kort.
- Implementere køsystem. 