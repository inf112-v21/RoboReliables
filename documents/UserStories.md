### Som spiller ønsker jeg å se spillebrettet for å kunne få oversikt.

**Akseptansekriterier**:

- Vise et komplett brett.

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

### Som spiller ønsker jeg å kunne spille med andre spillere over nett, slik at jeg fremdeles kan spille mot andre selv om vi ikke er i samme rom (samt at spillet blir mer COVID-vennlig).

**Akseptansekriterier**:

- Det er mulig å velge mellom å være vert eller å være klient.
- Spillet tillater at flere spillklienter kan koble seg til en vert gjennom nettet.
- Spillere (klienter) sender og mottar nødvendig informasjon frem og tilbake gjennom verten.
- Spillbrettet oppdateres for å reflektere oppdateringene som blir mottatt.

**Arbeidsoppgaver**:

- Implementere vert/klient-funksjonalitet.
- Implementere metode for å sende og motta spillere.
- Implementere socket-rammeverk.

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

- - - - 

### Som spiller ønsker jeg å kunne velge kort grafisk, for å lett og oversiktlig kunne se og velge kort.

**Akseptansekriterier**:

- Kortene vises på skjermen
- Kortene reagerer på musetrykk
- Kortene indikerer om de er valgt eller ikke
- Når spilleren har valgt kort, skal de kunne bekrefte valget sitt
- Når kortene har blitt valgt og spilleren er klar, vil disse kortene bli utført (og ingen andre)

**Arbeidsoppgaver**

- Lage HUD som vises på spillskjermen
- Finne/lage teksturer til kort, samt generere atlas for disse teksturene
- Implementere lasting av riktige kort når nye kort deles ut
- Implementere `Listeners` til kortene slik at de reagerer når de trykkes på
- Implementere funksjonalitet slik at når et kort trykkes på, endres statusen (toggle valgt/ikke valgt)
- Implementere visuell indikasjon på om et gitt kort er valgt eller ikke
- Implementere logikk slik at kortene som blir valgt stemmer overens med kortene som blir lagt i robotregisteret og senere utført
- Implementere "Arm"-knapp (ready-knapp) som lar spilleren bekrefte valget sitt

- - - - 

### Som spiller ønsker jeg å bli møtt med en spillmeny når jeg starter spillet, slik at jeg lett kan velge spillmodus og antall spillere.

**Akseptansekriterier**

- Menyen vises på skjermen når man starter spillet
- Menyen består av knapper som reagerer ved musetrykk
- Disse knappene lar spilleren velge spillmodus, velge antall spillere, og starte/avslutte spillet
- Det er lett å se hvilke innstillinger som har blitt valgt
- Når man trykker "start"-knappen, starter spillet i tråd med innstillingene som har blitt valgt

**Arbeidsoppgaver**

- Implementere en Meny-skjerm-klasse
- Legge til ressurser for menyen (logo, knapptekstur osv.)
- Implementere menyknapper
- Implementere funksjonalitet for hver knapp
- Implementere glider for å velge antall spillere, og avkrysningsrute for å bestemme diverse av/på parametre
- Implementere logikk for overgangen fra meny til spillskjerm

### Som spiller ønsker jeg at det vises en tekstkonsoll på spillskjermen, slik at jeg blir informert om diverse hendelser i spillet.

**Akseptansekriterier**

- Det vises en tekstkonsoll på skjermen
- Denne konsollen:
    - forteller om hvilke kort som blir utført (og i hvilken rekkefølge)
	- gir tilbakemelding på spillerens valg (vellykket/ikke vellykket)
- Når tekstkonsollen fylles opp, blir gammel tekst fjernet (ordentlig scrolling-oppførsel)

**Arbeidsoppgaver**

- Implementere et eget objekt i HUD som representerer tekstkonsollen
- Plassere tekstkonsollen på et fornuftig sted i spillskjermen
- Lage metode som printer til tekstkonsollen
- Bruke denne metoden til å skrive informasjon i tekstkonsollen som beskrevet i akseptansekriteriene
- Implementere scrolling-oppførsel i tekstkonsollen

### Som spiller ønsker jeg at det er musikk i spillet, slik at ørene mine ikke føler seg ensomme.

**Akseptansekriterier**

- Når spillet starter vil musikken begynne å spille, og holde frem helt til spillet avsluttes

**Arbeidsoppgaver**

- Komponere musikken, og legge det inn i `assets`
- Ta bruk av LibGDX sin funksjonalitet for å håndtere avspilling av musikken
