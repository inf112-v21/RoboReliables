
### Som spiller ønsker jeg å se spillebrettet for å kunne få oversikt.

**Akseptansekriterier**:

- Vise et komplett brett.

**Arbeidsoppgaver**:

- Lage GUI.

### Som spiller ønsker jeg å se hvor roboten min er på brettet for å kunne vite hvor brikken er.

**Akseptansekriterier**:

- Vise spillerens robot på spillebrettet.

**Arbeidsoppgaver**:

- Opprette grafikk for spiller.

### Som utvikler ønsker jeg å kunne flytte posisjonen til en brikke for å kunne teste «victory-condition».

**Akseptansekriterier**:

- Robot beveger seg i relasjon til input fra tastatur.

**Arbeidsoppgaver**:

- Implementere input funksjon fra spiller.

### Som spiller ønsker jeg å kunne flytte brikken min til flagget for å kunne vinne spillet.

**Akseptansekriterier**:

- Brikken og flagget kan okkupere samme felt på spillebrettet.
- Spilleren kan flytte på brikken sin.
- Det må være et flagg på brettet.
- Spiller får vite at de har vunnet dersom de flytter brikken sin gjennom alle flaggene på brettet i riktig rekkefølge.

**Arbeidsoppgaver**:

- Vise flagg.
- Implementere kode for «win-condition».
- Implementere funksjonalitet for å sjekke posisjon til spiller.
- Feilhåndtering av «win-condition».


### Som spiller ønsker jeg at det er andre spillere med sånn at jeg kan spille mot de.

**Akseptansekriterier**:

- Man kan spesifisere antall spillere.
- Hver spiller vises som en brikke på brettet.
- Den aktive spiller byttes på mellom hver runde.
- Den aktive spiller får kontroll over bevegelse og å endre sin runde.

**Arbeidsoppgaver**:

- Gi mulighet til å spesifisere antall spillere i begynnelsen av spillet.
- Implementere runder.

### Som spiller ønsker jeg å få utdelt kort fra en kortstokk for å kunne bruke kortene mine senere.

**Akseptansekriterier**:

- Hver spiller har en inventory hvor kortene til spilleren oppbevares.
- Spillet gir ut x antall kort til hver spiller.
- Ha en sentral kortstokk som inneholder spillets kort.

  **Arbeidsoppgaver**:

- Implementere inventory til en spiller.
- Lage funksjon som deler ut kort.
- Representere alle spillets kort.
