Splash and Jump est un jeu d'aventure en 2D où le joueur contrôle un personnage principal 
naviguant à travers différents niveaux remplis d'obstacles, d'ennemis et de plateformes mobiles. 

Le but du jeu est de collecter des fruits, éviter les ennemis et les obstacles, et atteindre la fin du niveau 2.


Mécaniques de Jeu

Saut et Gravité :
Le personnage peut sauter lorsqu'il est au sol en utilisant la touche Z ou l'espace ou le fleche droite.
La gravité fait retomber le personnage après un saut, donc il risque de tomber dans des trous ou sur des ennemis.

Plateformes Mobiles :
Les plateformes se déplacent horizontalement, Il faut sauter dessus.

Collecte de Fruits :
Les fruits sont dispersés dans les niveaux. 
Il faut les toucher pour les collecter afin d’ajouter des points a la barre de vie.

Collision avec Obstacles :
Il faut éviter les tuiles d'obstacles et les bords de l'écran. Certaines peuvent faire perdre de la vie.

Barre de Vie :
La barre de vie du personnage est visible au dessus de lui. 
Il suffit d'éviter de perdre de la vie pour ne pas atteindre la fin du jeu .

Passage au Niveau Suivant :
Pour passer au niveau suivant, le joueur doit atteindre le bord droit de l'écran du premier niveau.


Description des classes: 

Entity: 
La classe abstraite Entity est la base pour toutes les entités du jeu (joueur, poisson, fruit, etc.). 
Elle contient des champs pour la position, la vitesse et les images, ainsi que des méthodes pour mettre à jour et dessiner l'entité.

Fish: 
La classe Fish est une classe dérivée de Entity.
Elle gère l'image du poisson et ses mouvements verticaux aléatoires.

Fruit: 
Fruit est une classe dérivée de Entity permettant de gérer l'image du fruit et sa visibilité.

Platform: 
Platform dérivée de  Entity gère l'image et le mouvement d'une plateforme mobile.

Player:
La classe Player représente le personnage contrôlé par le joueur et gère son mouvement, sa collision avec les objets du jeu et sa gestion de la vie.

Gamepanel: 
La classe GamePanel gère l'affichage du jeu et la mise à jour des éléments du jeu.

Keyhandler: 
La classe KeyHandler gère les entrées clavier du joueur.


@Auteur:
SAMMARI Eya, MEZIANE Zakariae, CHERIF Mey, ZEROUAL Ouiam, MARZOUKI Youssef.



![Screen1](https://github.com/eyasam/ProjetSplashESIR1/assets/73797676/1e2248bc-5c26-477c-b85a-4e4aed1ded6a)
![Screen2](https://github.com/eyasam/ProjetSplashESIR1/assets/73797676/6360a559-034c-44d0-a0c8-b71274980d66)
