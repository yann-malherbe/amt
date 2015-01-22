---
title: FAQ
template: layout.jade
subsectionIndex: 2
---

###Questions###

- PUT /organizations/{id}. "If you change the person of contact, the user always belong to the organization." Je ne suis pas sûr de comprendre ce que ça veut dire. "The user", c'est l'utilisateur qui était personne de contact avant? La gestion des personnes de contact mériterait un traitement particulier dans la doc. En commençant à parcourir votre doc, je CROIS comprendre que je dois d'abord faire un POST pour créer une organisation (sans personne de contact), suivi d'un PUT pour attribuer un utilisateur. Mais je ne suis pas sûr...
	- *En effet, il faut d'abord ajouter une organization et en suite un utilisateur*

- GET /users/. Vous décrivez un query string paramter "id". Est-ce que ça voudrait dire que je peux faire un GET /users/123 ou un GET /users?id=123 pour accéder à la même ressource? Un peu surprenant, je n'offrirais pas le query string parameter. En revanche, est-ce que j'ai un moyen pour récupérer les utilisateurs d'une organisation?
	- *Non, le querystring id ne doit être utilisé sans le querystring order, exemple : GET /users?order=byOrganizationId&id=1 *