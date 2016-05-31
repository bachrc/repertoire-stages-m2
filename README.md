# Répertoire de Stages M2

Ce projet est dans le cadre du TPE de première année du Master SIRES. Il a été réalisé par Yohann BACHA, Romain FOLÂTRE avec l'encadrement de M. Frédéric GUINAND.

Le projet consiste en le développement d'une application permettant la consultation des entreprises ayant déjà pris des stagiaires auparavant, afin que les nouveaux étudiants en Master 2 puissent connaître les entreprises ayant potentiellement besoin d'un stage.

Le principal challenge de cette application est l'affichage ergonomique des informations de la base de données, ainsi que la mise à jour de cette dernière !

### Téléchargement de l'installeur

Vous disposez sur [la page de téléchargement](https://github.com/totorolepacha/repertoire-stages-m2/releases) de la liste des installeurs mis à votre disposition, téléchargeables, ainsi que les codes source de chaque version.

### Compilation des sources

Avant de vous entreprendre dans la compilation de l'application, qui peut vous prendre du temps, assurez vous de posséder le JDK 7 au minimum, le SDK 16 de Google, ainsi que git, et de posséder leurs emplacements dans les variables d'environnement de votre système.

Une fois que vous vous en êtes assurés, clonez le projet dans le dossier de votre choix :

    git clone https://github.com/totorolepacha/repertoire-stages-m2.git

Rendez-vous ensuite dans le répertoire créé :

    cd repertoire-stages-m2

La procédure de compilation change selon l'OS sur lequel vous vous trouvez :

- Windows :

```
gradlew.bat assembleDebug
```

- Linux / Mac OS :

```
chmod +x gradlew
./gradlew assembleDebug
```

Gradle va ici télécharger les composants nécéssaires à la compilation du code. Quelques giga-octets de mémoires sont à prévoir, ainsi que de la patience.

Il ne vous reste plus qu'à installer l'application .apk résultée.

<!--
- Difficultés

Récupérer une erreur SQL avec SQLite
La lecture précise d'un CSV : OpenCSV avec Maven
L'établissement d'un classeur simple pour l'export et la saisie
Etablir un environnement de travail et de versionnement.

La fonction acos qui n'existe pas sous SQLite. Impossible de faire une requête simple. 

    SELECT * FROM Entreprise WHERE abbr IN(SELECT DISTINCT abbr FROM Localisation WHERE acos(sin(?) * sin(Lat * (3.14159265359/180)) + cos(?) * cos(Lat * (3.14159265359/180)) * cos(? - (Lon * (3.14159265359/180)))) * 6371 <= ?) --!>;



dddd