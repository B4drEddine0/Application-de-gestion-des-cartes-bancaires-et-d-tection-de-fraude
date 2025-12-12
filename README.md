<div align="center">
  <h1>Application de gestion des cartes bancaires et détection de fraude</h1>
  <p>
    Application Java 17 en ligne de commande pour gérer les cartes bancaires, leurs opérations
    et la détection de fraudes potentielles, avec persistance via JDBC/MySQL.
  </p>
</div>

<hr>

<h2>📌 Contexte du projet</h2>
<p>
  La gestion du cycle de vie des cartes bancaires et la détection de comportements suspects
  sont des enjeux majeurs pour les banques (multi-cartes, paiements, retraits, achats en ligne).
  Cette application permet de gérer les clients, leurs cartes, les opérations associées, et de
  déclencher des alertes de fraude selon des règles métier (montants élevés, opérations rapprochées
  dans des lieux différents, dépassement de plafond, etc.).
</p>

<h2>🎯 Objectifs et fonctionnalités</h2>
<ul>
  <li>Gérer le cycle de vie des cartes : création, activation, suspension, blocage, renouvellement.</li>
  <li>Enregistrer et consulter les opérations : achat, retrait, paiement en ligne, filtrage par date/type.</li>
  <li>Détecter automatiquement les anomalies et générer des alertes de fraude (INFO, AVERTISSEMENT, CRITIQUE).</li>
  <li>Produire des rapports : top 5 des cartes les plus utilisées, statistiques mensuelles par type d’opération, liste des cartes bloquées ou suspectes.</li>
  <li>Importer des cartes ou opérations depuis un fichier (ex. Excel) via un service d’import/export.</li>
</ul>

<h2>🏗️ Architecture et couches</h2>
<p>L’application suit une architecture en couches claire (Entity, DAO, Service, UI).</p>

<h3>Couche UI (présentation)</h3>
<ul>
  <li>Menu textuel en console.</li>
  <li>Créer un client.</li>
  <li>Émettre une carte (débit, crédit, prépayée).</li>
  <li>Effectuer une opération (achat, retrait, paiement en ligne).</li>
  <li>Consulter l’historique d’une carte.</li>
  <li>Lancer une analyse de fraude.</li>
  <li>Bloquer ou suspendre une carte.</li>
</ul>

<h3>Couche Services (métier)</h3>
<ul>
  <li><code>ClientService</code> : gestion des clients, recherche par email/téléphone.</li>
  <li><code>CarteService</code> : création/activation/blocage de carte, vérification des plafonds.</li>
  <li><code>OperationService</code> : enregistrement et recherche d’opérations par carte ou client.</li>
  <li><code>FraudeService</code> : détection d’anomalies et génération d’alertes.</li>
  <li><code>RapportService</code> : génération des statistiques et rapports.</li>
  <li><code>ImportExportService</code> : import de données (cartes, opérations) depuis un fichier externe.</li>
</ul>

<h3>Couche DAO (accès aux données)</h3>
<ul>
  <li><code>ClientDAO</code> : CRUD sur les clients.</li>
  <li><code>CarteDAO</code> : CRUD sur les cartes, recherche par client.</li>
  <li><code>OperationDAO</code> : CRUD sur les opérations, filtrage par carte/type/date.</li>
  <li><code>AlerteDAO</code> : CRUD sur les alertes, recherche par carte.</li>
</ul>

<h3>Couche Entity (modèle)</h3>
<ul>
  <li><code>Client</code> (record) : id, nom, email, téléphone.</li>
  <li>
    <code>Carte</code> (sealed class) : id, numero, dateExpiration, statut (ACTIVE, SUSPENDUE, BLOQUEE), idClient.
    <ul>
      <li><code>CarteDebit</code> : plafond journalier.</li>
      <li><code>CarteCredit</code> : plafond mensuel, taux d’intérêt.</li>
      <li><code>CartePrepayee</code> : solde disponible.</li>
    </ul>
  </li>
  <li><code>OperationCarte</code> (record) : id, date, montant, type (ACHAT, RETRAIT, PAIEMENTENLIGNE), lieu, idCarte.</li>
  <li><code>AlerteFraude</code> (record) : id, description, niveau (INFO, AVERTISSEMENT, CRITIQUE), idCarte.</li>
</ul>

<h3>Couche utilitaire</h3>
<ul>
  <li>Vérification des règles de fraude (montant, fréquence, lieux différents).</li>
  <li>Gestion des dates et des lieux.</li>
  <li>Génération de numéros de carte uniques (simulés).</li>
</ul>

<h2>🗄️ Modèle de données (MySQL)</h2>
<ul>
  <li><strong>Client</strong> : id, nom, email, téléphone.</li>
  <li><strong>Carte</strong> : id, numero, dateExpiration, statut, typeCarte, idClient.</li>
  <li><strong>OperationCarte</strong> : id, date, montant, type, lieu, idCarte.</li>
  <li><strong>AlerteFraude</strong> : id, description, niveau, idCarte.</li>
</ul>
<p>
  Relations : 1..n entre Client et Carte, 1..n entre Carte et OperationCarte, 1..n entre Carte et AlerteFraude.
</p>

<h2>🛠️ Technologies utilisées</h2>
<ul>
  <li>Java 17 (records, sealed classes, Stream API, Optional, lambdas).</li>
  <li>JDBC + MySQL.</li>
  <li>Architecture en couches (Entity, DAO, Service, UI).</li>
  <li>Gestion des exceptions, conventions Java.</li>
  <li>Git pour le suivi de version.</li>
</ul>

<h2>🚀 Lancer le projet</h2>
<ol>
  <li>Cloner le dépôt :
    <pre><code>git clone https://github.com/B4drEddine0/Application-de-gestion-des-cartes-bancaires-et-d-tection-de-fraude.git</code></pre>
  </li>
  <li>Créer la base MySQL et les tables selon le modèle décrit ci-dessus.</li>
  <li>Configurer les paramètres JDBC (URL, utilisateur, mot de passe).</li>
  <li>Compiler et exécuter le projet (IDE ou JAR exécutable).</li>
</ol>

<h2>📊 Diagramme de classes</h2>
<p>
  Le diagramme de classes doit refléter les entités (<code>Client</code>, <code>Carte</code> et sous-types,
  <code>OperationCarte</code>, <code>AlerteFraude</code>), leurs relations (1..n) et la séparation en couches
  (Entity, DAO, Service, UI).
</p>
