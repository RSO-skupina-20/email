# Pošiljanje emailov - Kafka
Mikrostoritev bo zagotavljala pošiljanje email povabil uporabnikom, ki so bili povabljeni na dogodek.

Funkcionalnosti aplikacije:
- Pošiljanje emaila: Pošiljanje emaila povabil uporabnikom, ki so bili povabljeni na dogodek

# 1. Dodajanje .env datoteke
- Dodati je potrebno FROM_DOMAIN in TOKEN, ki ju generirano na strani `https://app.mailersend.com/`


# 2. Zagon mikrostoritve v Docker okolju (lokalno)
- Ustvarjanje jar datoteke `mvn clean package`
- Ustvarjanje Docker slike `docker build -t email-api .`
- Zagon Docker kontejnerja na portu 8083 `docker run --env-file .env -p 8083:8080 email-api`

# 3. Env za mikrostoritev upravljanje dogodkov
V mikrostoritvi upravljanje dogodkov je potrebno dodati env spremenljivko ENVIROMENT z vrednostjo `dev`, ki omogoča da le ta uporavlja mikrostoritev za pošiljanje emailov. Če je vrednost kaj drugega, se mikrostoritev za pošiljanje emailov ne uporablja (zato ker na Azure nismo dodali Kafke in Zookeeperja)

# 4. Nastavitev Kafke in Zookeeperja
Navodila za zagon Kafka in Zookeeperja so zapisana pri mikrostoritvi upravljanje dogodkov.