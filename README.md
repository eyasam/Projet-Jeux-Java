# Splash and Jump

**Splash and Jump** est un jeu d'aventure en 2D où le joueur contrôle un personnage principal naviguant à travers différents niveaux remplis d'obstacles, d'ennemis et de plateformes mobiles.

Le but du jeu est de **collecter des fruits**, **éviter les ennemis et les obstacles**, et **atteindre la fin du niveau 2**.


## 🎮 Mécaniques de Jeu

### 🕹️ Saut et Gravité
- Le personnage peut sauter lorsqu'il est au sol en utilisant :
  - **Z**
  - **Espace**
  - **Flèche droite**
- La gravité fait retomber le personnage après un saut, ce qui peut entraîner une chute dans des trous ou sur des ennemis.

### 📦 Plateformes Mobiles
- Certaines plateformes se déplacent horizontalement.
- Le joueur doit **sauter dessus** au bon moment pour avancer.

### 🍎 Collecte de Fruits
- Les fruits sont dispersés dans les niveaux.
- **Touchez-les** pour les collecter et gagner des points qui augmentent la barre de vie.

### ⚡ Collision avec Obstacles
- Évitez les **tuiles d'obstacles** et les bords de l'écran.
- Certains obstacles peuvent faire **perdre de la vie**.

### ❤️ Barre de Vie
- La barre de vie du personnage est visible au-dessus de lui.
- Si la barre atteint zéro, la partie est terminée.

### ➡️ Passage au Niveau Suivant
- Pour passer au niveau suivant, atteignez **le bord droit de l'écran** du niveau actuel.


## 🛠️ Description des Classes

### `Entity`
- Classe abstraite servant de base pour toutes les entités du jeu (joueur, poisson, fruit, etc.).
- Champs principaux : position, vitesse, images.
- Méthodes principales : mise à jour et dessin de l'entité.

### `Fish`
- Classe dérivée de `Entity`.
- Gère l'image du poisson et ses **mouvements verticaux aléatoires**.

### `Fruit`
- Classe dérivée de `Entity`.
- Gère l'image du fruit et sa **visibilité**.

### `Platform`
- Classe dérivée de `Entity`.
- Gère l'image et le **mouvement** des plateformes mobiles.

### `Player`
- Représente le personnage contrôlé par le joueur.
- Gère :
  - **Mouvements**
  - **Collisions** avec les objets
  - Gestion de la **vie**

### `GamePanel`
- Gère l'affichage du jeu.
- Met à jour les éléments du jeu.

### `KeyHandler`
- Gère les **entrées clavier** du joueur.



## 🖼️ Captures d'Écran
*Un exemple de niveau avec des plateformes mobiles et des fruits à collecter.*

<img src="https://github.com/eyasam/ProjetSplashESIR1/assets/73797676/1e2248bc-5c26-477c-b85a-4e4aed1ded6a"  width="600"/>

*Le personnage sautant au-dessus d’un obstacle.*

<img src="https://github.com/eyasam/ProjetSplashESIR1/assets/73797676/6360a559-034c-44d0-a0c8-b71274980d66" width="600"/>

