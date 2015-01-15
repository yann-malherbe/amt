##Magali##

####Repo GitHub####
--------------------

- La première impression est positive: les fichiers sont bien organisés, dans des dossiers thématiques. Il y a des instructions que je vais pouvoir suivre pour évaluer la plate-forme et je vois qu'il y a des scripts à disposition. Il y a aussi des liens vers la doc d'API.

- J'aurais peut-être aimé avoir un peu plus de contenu au niveau de la description de la plate-forme. La question que vous devez vous poser est la suivante: si un développeur qui arrive sur ce repo n'a jamais entendu parler du projet, est-ce qu'il va RAPIDEMENT se faire une idée ce que le projet offre et aura-t-il ENVIE de FAIRE L'EFFORT de cloner le repo et de le tester. Ce serait donc bien de décrire ce que fait votre service, d'inclure un schéma d'architecture. Vous avez construit un IHM, faites un screenshot et incluez le dans le README.md - ça fera envie aux gens! En d'autres termes, mais si c'est le repo d'un projet de cours, j'aimerais que vous fassiez l'exercice de le considérer comme le repo d'un projet que vous avez envie de mettre à disposition des autres. Réfléchissez-y pour la version finale.


####API REST####
----------------

- Vous avez un peu épuré/customisé le template (suppression de l'onglet Blog). Il manque un peu de contenu sur la page "Home", où on aimerait bien trouver une description du modèle de domaine (un peu plus fournie que les 2-3 phrases que vous avez, mais dans cet esprit). La page Overview a été nettoyée (suppression de PATCH), mais je suis curieux de voir si vous avez déjà implémenté la sécurité. Faites bien attention à garder la doc et l'implémention en sync. 

- GET /organizations: [{ "id" : 1, "name" : "AMT", "contact" : {"id": 1}}]. On pourrait discuter la partie "personne de contact". D'abord, on pourrait avoir l'info "à plat" ("contactPersonId" : 1). Ensuite, on peut se poser la question de savoir si on veut envoyer l'id (ce qui oblige le client à connaître le préfixe pour accéder au end-point /users et qui crée un certain couplage, puisque ce préfixe ne pourra plus changer), ou si veut envoyer l'url ("contactPerson" : "/users/1"). Beaucoup de concepteurs d'APIs recommandent cette approche (qui est un aspect du concept HATEOAS).

- GET /organizations/{id}. "Retrieve an organization (id, name, list of user, contact person). A user work to only one organization.". La partie "list of user" m'interroge. Je ne vois pas cette partie dans l'exemple. Je ne pense pas qu'il serait idéal d'envoyer la liste des utilisateurs dans le payload Organization (je préfèrerais personnellement y accéder par un endpoint /users?byOrg=orgId ou un endpoint /organizations/1/users).

- PUT /organizations/{id}. "If you change the person of contact, the user always belong to the organization." Je ne suis pas sûr de comprendre ce que ça veut dire. "The user", c'est l'utilisateur qui était personne de contact avant? La gestion des personnes de contact mériterait un traitement particulier dans la doc. En commençant à parcourir votre doc, je CROIS comprendre que je dois d'abord faire un POST pour créer une organisation (sans personne de contact), suivi d'un PUT pour attribuer un utilisateur. Mais je ne suis pas sûr...

- GET /users/. Vous décrivez un query string paramter "id". Est-ce que ça voudrait dire que je peux faire un GET /users/123 ou un GET /users?id=123 pour accéder à la même ressource? Un peu surprenant, je n'offrirais pas le query string parameter. En revanche, est-ce que j'ai un moyen pour récupérer les utilisateurs d'une organisation?

- PUT /users/{id}. Quelque chose d'un peu bizarre au niveau du mot de passe: on dirait qu'on ne peut pas l'envoyer dans la requête (bizarre) et qu'on le reçoit dans la réponse (doublement bizarre).

- GET /sensors/. Même remarque pour le query string parameter "id".

- GET /sensors/{id}. Je pinaille, mais dans l'EXEMPLE de réponse, open devrait être soit true, soit false ({"id" : 1, "create_date": "2014-08-11", open : "true/false", "organization" : {"id" : 1}})

- POST /observations. Même remarque qu'avant (et ça vaut pour toutes les ressources), j'enverrai plutôt l'id de capteur "à plat", dans le payload JSON ("sensorId" : 123).

- GET /facts/summaries et GET /facts/numbers. C'est une question intéressante. Ici, vous avez encodé le TYPE DE FACT dans l'URL. Avec cette logique, ça voudrait dire que chaque fois qu'on va définir un nouveau type de FACT (ce que j'imagine), on verrait le nombre de endpoints grandir de manière continue et conséquente. Pour cette raison, je verrais plutôt un filtre: GET /facts?byType=summaryFact, GET /facts?byType=counterFact). "Counter" plutôt que "Number".

- DELETE /facts. Vous voulez vraiment autoriser la suppression des Facts? Pourquoi pas, mais j'aimerais bien savoir pourquoi (c'est pour une fonction de reset?)


####Installation / premiers pas####
-----------------------------------

- J'ai réussi à déployer et à exécuter du premier coup. Je fais un run et j'arrive sur une UI, excellent. Je lance le script de création des objets et je vois les données dans l'IHM, excellent. Je lance l'autre script et je vois les données dans les graphes, super!

- Je passe à POSTMAN. Attention, le préfixe de l'URL dans la doc inclut un "v1" qui n'est pas utilisé dans le code.

- Je commence par un GET /organizations. Ca fonctionne, mais le format de la réponse ne correspond pas exactement à la doc (et remet en cause mes commentaires par rapport aux id que je recommendais d'envoyer "à plat"). En effet, je constate que pour la personne de contact, j'obtiens non seulement un ID, mais aussi d'autres attributs.

- Je fais un test pour vérifier la question au sujet de l'id qui serait accepté à la fois dans le path et dans le query string. Je fais un GET http://localhost:8080/project1/api/users/2, suivi d'un GET http://localhost:8080/project1/api/users?id=2. Je n'obtiens pas la même réponse, qui me laisse penser que la doc n'est pas tout à fait correcte.

- Je fais un GET http://localhost:8080/project1/api/observations et j'ai l'impression que tout plante (Chrome fige). Ca me fait penser que j'ai oublié une remarque au niveau de la conception de l'API. On voit que donner accès à toutes les observations, sans pagination, n'est pas une bonne idée (consommation excessive de ressources). Même avec de la pagination, si vous voulez offrir ce service, ça va vous coûter très cher (stockage, CPU pour faire les requêtes dans la DB).

- Je fais un POST pour créer une organisation (mais sans savoir si je pourrais directement passer une personne de contact). Ca fonctionne.

- Je fais 2 tests pour récupérer les Facts. Ca fonctionne. Quelque chose de très bien (par rapport à ce que la plupart des groupes ont fait), est que vous envoyez les propriétés (min, max, avg, count) comme attributs du payload JSON (et pas dans une chaîne de caractères où ces valeurs seraient concaténées). Vous avez également ces valeurs dans des colonnes dans les tables de la DB, ce qui me permet de faire des requêtes sur les propriétés des Facts en SQL (avoir une colonne "properties" avec la concaténation des valeurs de le permettrait pas). Une alternative serait d'avoir, dans le json, un attribut de type tableau appelé "properties". Cela faciliterait un peu l'utilisation du payload dans le client javascript, surtout dans le cas où le client ne connaît pas à priori toutes les propriétés d'un type de fact.

- En termes de payload, envoyer les attributs du capteur avec le fact n'est peut-être pas optimal (peut-être mieux d'envoyer une référence). A discuter.

- Jusque là, c'est très bien. Une chose à considérer pour la dernière étape, c'est d'avoir un moyen pour valider le calcul des Facts. Là, je vois que des choses se passent, mais je n'ai pas de preuve que le calcul est correct (est-ce que, par exemple, on est protégé des problèmes de concurrence?)

####Code####
-----------------------------------

- Dans les DAO, le "flush" est nécessaire uniquement lors de l'ajout. On l'utilise, parce qu'on veut que l'INSERT soit envoyé tout de suite (avant que la transation soit committée par le container) de manière à obtenir l'id.

- Dans les entités, vous utilisez la stratégie par défaut pour attribuer les IDs aux entités. C'est pour cela que la table SEQUENCE est générée et utilisée. Personnellement, j'aime mieux la stratégie GenerationType.IDENTITY qui utilise les colonnes de type auto-increment.

- Dans les DTO, vous avez bien fait les choses dans la gestion des objets complexes. Ce que je veux dire, c'est que par exemple votre classe ObservationDTO fait référence à une classe SensorDTO et pas à la classe Sensor (ce que j'ai vu chez d'autres groupes). Par contre, comme déjà mentionné auparavant, il serait intéressant de challenger le choix d'envoyer 1) des structures complexes, 2) des références sous la forme d'un id et 3) des référence sous la forme d'une URL. Le choix dépend du DTO.

- Dans ObservationResource, vous avez votre méthode createObservation. Point positif, vous n'avez pas mis la logique métier de création/mise à jour des Facts directement dans cette méthode et l'avez extraite dans d'autres méthodes. C'est plus propre. Je pousserais plus loin en introduisant un service (dans un package services.business, à côté d'un package services.dao). Vous auriez alors la structure suivante:

	Client --> ObservationResource.createObservation
           --> services.dao.ObservationsManager.create (on ne fait que persister)
           --> services.business.ObservationsFlowProcessor.processObservation (on met à jour les facts)
               --> services.dao.FactsManager.create/update

- Je pinaille, mais "factCounterManager" est un peu bizarre comme nom de méthode (j'aurais plutôt vu quelque chose comme updateCounterFacts())

- la logique de calcul des facts est assez compliquée. Je n'ai pas vérifié tout le code et je ne me peux pas me prononcer sur le fait qu'il soit tout à fait correct ou pas. Concevoir une procédure de validation pour cette partie sera la principale tâche pour terminer le projet!


##Yann##
####API REST####
-----------------------------------

- GET /organizations: [{ "id" : 1, "name" : "AMT", "contact" : {"id": 1}}].
	- On pourrait discuter la partie "personne de contact". D'abord, on pourrait avoir l'info "à plat" ("contactPersonId" : 1). 
	- Ensuite, on peut se poser la question de savoir si on veut envoyer l'id (ce qui oblige le client à connaître le préfixe pour accéder au end-point /users et qui crée un certain couplage, puisque ce préfixe ne pourra plus changer), ou si veut envoyer l'url ("contactPerson" : "/users/1"). Beaucoup de concepteurs d'APIs recommandent cette approche (qui est un aspect du concept HATEOAS).

- GET /organizations/{id}. "Retrieve an organization (id, name, list of user, contact person). A user work to only one organization.". La partie "list of user" m'interroge. Je ne vois pas cette partie dans l'exemple. Je ne pense pas qu'il serait idéal d'envoyer la liste des utilisateurs dans le payload Organization (je préfèrerais personnellement y accéder par un endpoint /users?byOrg=orgId ou un endpoint /organizations/1/users).

- GET /users/. Vous décrivez un query string paramter "id". Est-ce que ça voudrait dire que je peux faire un GET /users/123 ou un GET /users?id=123 pour accéder à la même ressource? Un peu surprenant, je n'offrirais pas le query string parameter. En revanche, est-ce que j'ai un moyen pour récupérer les utilisateurs d'une organisation?

- PUT /users/{id}. Quelque chose d'un peu bizarre au niveau du mot de passe: on dirait qu'on ne peut pas l'envoyer dans la requête (bizarre) et qu'on le reçoit dans la réponse (doublement bizarre).

- GET /sensors/. Même remarque pour le query string parameter "id".

- POST /observations. Même remarque qu'avant (et ça vaut pour toutes les ressources), j'enverrai plutôt l'id de capteur "à plat", dans le payload JSON ("sensorId" : 123).

- GET /facts/summaries et GET /facts/numbers. C'est une question intéressante. Ici, vous avez encodé le TYPE DE FACT dans l'URL. Avec cette logique, ça voudrait dire que chaque fois qu'on va définir un nouveau type de FACT (ce que j'imagine), on verrait le nombre de endpoints grandir de manière continue et conséquente. Pour cette raison, je verrais plutôt un filtre: GET /facts?byType=summaryFact, GET /facts?byType=counterFact). "Counter" plutôt que "Number".

- DELETE /facts. Vous voulez vraiment autoriser la suppression des Facts? Pourquoi pas, mais j'aimerais bien savoir pourquoi (c'est pour une fonction de reset?)

####Installation / premiers pas####
-----------------------------------

- Je fais un test pour vérifier la question au sujet de l'id qui serait accepté à la fois dans le path et dans le query string. Je fais un GET http://localhost:8080/project1/api/users/2, suivi d'un GET http://localhost:8080/project1/api/users?id=2. Je n'obtiens pas la même réponse, qui me laisse penser que la doc n'est pas tout à fait correcte.

- Je fais un GET http://localhost:8080/project1/api/observations et j'ai l'impression que tout plante (Chrome fige). Ca me fait penser que j'ai oublié une remarque au niveau de la conception de l'API. On voit que donner accès à toutes les observations, sans pagination, n'est pas une bonne idée (consommation excessive de ressources). Même avec de la pagination, si vous voulez offrir ce service, ça va vous coûter très cher (stockage, CPU pour faire les requêtes dans la DB).

- Je fais un POST pour créer une organisation (mais sans savoir si je pourrais directement passer une personne de contact). Ca fonctionne.

- Je fais 2 tests pour récupérer les Facts. Ca fonctionne. Quelque chose de très bien (par rapport à ce que la plupart des groupes ont fait), est que vous envoyez les propriétés (min, max, avg, count) comme attributs du payload JSON (et pas dans une chaîne de caractères où ces valeurs seraient concaténées). Vous avez également ces valeurs dans des colonnes dans les tables de la DB, ce qui me permet de faire des requêtes sur les propriétés des Facts en SQL (avoir une colonne "properties" avec la concaténation des valeurs de le permettrait pas). Une alternative serait d'avoir, dans le json, un attribut de type tableau appelé "properties". Cela faciliterait un peu l'utilisation du payload dans le client javascript, surtout dans le cas où le client ne connaît pas à priori toutes les propriétés d'un type de fact.

- En termes de payload, envoyer les attributs du capteur avec le fact n'est peut-être pas optimal (peut-être mieux d'envoyer une référence). A discuter.

####Code####
-------------

- Dans les DAO, le "flush" est nécessaire uniquement lors de l'ajout. On l'utilise, parce qu'on veut que l'INSERT soit envoyé tout de suite (avant que la transation soit committée par le container) de manière à obtenir l'id.

- Dans les entités, vous utilisez la stratégie par défaut pour attribuer les IDs aux entités. C'est pour cela que la table SEQUENCE est générée et utilisée. Personnellement, j'aime mieux la stratégie GenerationType.IDENTITY qui utilise les colonnes de type auto-increment.

- Dans les DTO, vous avez bien fait les choses dans la gestion des objets complexes. Ce que je veux dire, c'est que par exemple votre classe ObservationDTO fait référence à une classe SensorDTO et pas à la classe Sensor (ce que j'ai vu chez d'autres groupes). Par contre, comme déjà mentionné auparavant, il serait intéressant de challenger le choix d'envoyer 1) des structures complexes, 2) des références sous la forme d'un id et 3) des référence sous la forme d'une URL. Le choix dépend du DTO.

- Dans ObservationResource, vous avez votre méthode createObservation. Point positif, vous n'avez pas mis la logique métier de création/mise à jour des Facts directement dans cette méthode et l'avez extraite dans d'autres méthodes. C'est plus propre. Je pousserais plus loin en introduisant un service (dans un package services.business, à côté d'un package services.dao). Vous auriez alors la structure suivante:

 	Client --> ObservationResource.createObservation
           --> services.dao.ObservationsManager.create (on ne fait que persister)
           --> services.business.ObservationsFlowProcessor.processObservation (on met à jour les facts)
               --> services.dao.FactsManager.create/update

- Je pinaille, mais "factCounterManager" est un peu bizarre comme nom de méthode (j'aurais plutôt vu quelque chose comme updateCounterFacts())


##Ruda##
- Jusque là, c'est très bien. Une chose à considérer pour la dernière étape, c'est d'avoir un moyen pour valider le calcul des Facts. Là, je vois que des choses se passent, mais je n'ai pas de preuve que le calcul est correct (est-ce que, par exemple, on est protégé des problèmes de concurrence?)

- la logique de calcul des facts est assez compliquée. Je n'ai pas vérifié tout le code et je ne me peux pas me prononcer sur le fait qu'il soit tout à fait correct ou pas. Concevoir une procédure de validation pour cette partie sera la principale tâche pour terminer le projet!