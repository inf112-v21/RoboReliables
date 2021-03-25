### Som spiller ønsker jeg å se spillebrettet for å kunne få oversikt.

**Akseptansekriterier**:

- Vise et komplett brett.

- - - - 

**Arbeidsoppgaver**:

- Lage GUI.

- - - - 

### Som spiller ønsker jeg å se hvor roboten min er på brettet for å kunne vite hvor brikken er.

**Akseptansekriterier**:

- Vise spillerens robot på spillebrettet.

**Arbeidsoppgaver**:

- Opprette grafikk for spiller.

- - - - 

### Som utvikler ønsker jeg å kunne flytte posisjonen til en brikke for å kunne teste «victory-condition».

**Akseptansekriterier**:

- Robot beveger seg i relasjon til input fra tastatur.

**Arbeidsoppgaver**:

- Implementere input-metode fra spiller.

- - - - 

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

- - - - 

### Som spiller ønsker jeg at det er andre spillere med sånn at jeg kan spille mot de.

**Akseptansekriterier**:

- Man kan spesifisere antall spillere.
- Hver spiller vises som en brikke på brettet.
- Den aktive spiller byttes på mellom hver runde.
- Den aktive spiller får kontroll over bevegelse og å endre sin runde.

**Arbeidsoppgaver**:

- Gi mulighet til å spesifisere antall spillere i begynnelsen av spillet.
- Implementere runder.

- - - - 

### Som spiller ønsker jeg å få utdelt kort fra en kortstokk for å kunne bruke kortene mine senere.

**Akseptansekriterier**:

- Hver spiller har en inventory hvor kortene til spilleren oppbevares.
- Spillet gir ut x antall kort til hver spiller.
- Ha en sentral kortstokk som inneholder spillets kort.
  
**Arbeidsoppgaver**:

- Implementere inventory til en spiller.
- Lage metode som deler ut kort.
- Representere alle spillets kort.

- - - - 

### Som spiller ønsker jeg å kunne spille med andre spillere over nett.

**Akseptansekriterier**:

- Det er mulig å velge mellom å være vert eller å være klient.
- Spillet tillater at flere spillklienter kan koble seg til en vert gjennom nettet.
- Spillere (klienter) sender og mottar nødvendig informasjon frem og tilbake gjennom verten.
- Spillbrettet oppdateres for å reflektere oppdateringene som blir mottatt.

**Arbeidsoppgaver**:

- Implementere vert/klient-funksjonalitet.
- Implementere metode for å sende og motta spillere.

- - - - 

### Som utvikler ønsker jeg at kortene skal ha en prioritetsverdi, for å reflektere det originale brettspillets regler.

**Akseptansekriterier**:

- Kortene har en prioritetsverdi.
- Spilleren kan se prioritetsverdien til kandidatkortene de kan velge mellom når de skal velge kort.
- Kortene blir utført i synkende rekkefølge basert på prioritetsverdien til hver spiller sitt neste kort.

**Arbeidsoppgaver**:

- Kortklassen skal ha en `int`-variabel for prioritetsverdi.
- Metoden som omhandler valg av kort må endres for å inkludere prioritetsverdien i sin utput.
- Gjøre kortene sammenlignbare basert på prioritetsverdien.
- Implementere et køsystem for kortene som har blitt valgt, og sortere de basert på prioritetsverdien.
- Utføre rundene basert på dette køsystemet.

- - - - 

### Som utvikler ønsker jeg å ha flere enn ett flagg, for å reflektere det originale brettspillets regler.

**Akseptansekriterier**:

- Det er flere enn ett flagg på brettet.
- Hvert flagg har unik posisjon.
- Hvert flagg sin plass i rekkefølgen er indikert på flagget sitt ikon.
- Det lagres hvilke flagg hver spiller har besøkt.
- Spiller vinner hvis de besøker alle flaggene i riktig rekkefølge.

**Arbeidsoppgaver**:

- Flaggklassen skal ha en `int`-variabel for plass i rekkefølgen.
- Spillerklassen skal ha en liste over hvilke flagg som har blitt besøkt.
- Denne listen oppdateres bare hvis riktig flagg i rekkefølgen blir besøkt.
- Implementere win-condition som beskrevet i akseptansekriterie 5.

