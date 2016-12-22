#Semestrální práce - 4IT475 - Systémové integrační testování
##Téma
Simulace webového API
##Zadání
Vaše společnost vyvíjí software, který má být integrován s aplikací TestLink (
http://testlab.tesena.com/testlink ) . Instalaci TestLinku do Vašeho prostředí zajišťuje externí firma,
termín dodání je až za dva měsíce. Vy ale už teď potřebujete testovat komunikaci mezi
komponentami vyvíjeného softwaru a TestLinkem.  
Prostudujte dokumentaci API, které poskytuje aplikace TestLink: 
* https://github.com/TestLinkOpenSourceTRMS/testlink-code/tree/testlink_1_9/lib/api  
* http://jetmore.org/john/misc/phpdoc-testlink193-api/TestlinkAPI/TestlinkXMLRPCServer.html

Vytvořte simulaci (mock) tohoto rozhraní.  
Simulace akceptuje všechny zprávy, které akceptuje reálná aplikace (XML-RPC – zpracuje volání
všech nabízených funkcí).  
Simulace nemá (nemusí mít) žádnou aplikační logiku – ve skutečnosti nic nedělá, jen se „tváří“ jako
simulovaná aplikace. Na validní požadavky odpovídá validní odpovědí – vždy stejnou, nebo lehce
parametrizovanou.  
Doporučujeme projekt vypracovat v SoapUI, není to však nutnost. Součástí práce vypracujte také
testy které prověří funkčnost simulace.
##Řešení
##Autoři
* Jan Sýkora
* David Král
* Michael Friedman