# CAHIER DES CHARGES - PROJET 2
## APPLICATION DÉTECTION MALADIES PLANTES AVEC IA

**Établissement :** [Nom de l'établissement]  
**Niveau :** Terminale TI  
**Encadreur :** [Votre nom]  
**Élèves :** 7 élèves  
**Durée :** 3 semaines  
**JPOE 2026 :** "La créativité à l'ère de l'IA : de l'artisanat à la haute technologie"

---

## 🎯 OBJECTIF

Développer une application mobile Android de détection des maladies des plantes utilisant l'intelligence artificielle pour identifier des anomalies et générer des recommandations de traitement adaptées au contexte agricole camerounais.

---

## 📱 FONCTIONNALITÉS MVP

### ✅ Ce que l'app DOIT faire :

**MODE HYBRIDE : 2 niveaux de détection**

### **1. MODE RAPIDE (Hors ligne - TensorFlow Lite)**
- Capture photo de la feuille malade
- Détection ML locale (modèle pré-entraîné)
- Résultat immédiat (1-2 secondes)
- 38 classes de maladies (modèle PlantVillage)
- Précision : 75-85%
- **Fonctionne sans internet**

### **2. MODE AVANCÉ (En ligne - Gemini Vision)**
- Upload photo vers serveur
- Analyse IA avancée (Google Gemini Vision)
- Détection précise avec % de confiance
- Recommandations personnalisées (contexte Cameroun)
- Précision : 90-95%
- **Nécessite connexion internet**

### **3. FONCTIONNALITÉS COMMUNES**
- Affichage résultat (maladie, symptômes, traitement)
- Historique local des détections
- Mode galerie (analyser photo existante)
- Conseils spécifiques au Cameroun

### ❌ Ce que l'app ne fait PAS (MVP) :

- Reconnaissance de 50+ maladies
- Chatbot conversationnel
- Géolocalisation des maladies
- Communauté d'agriculteurs
- Marketplace produits phyto
- Comptes utilisateurs

---

## 🏗️ ARCHITECTURE TECHNIQUE

```
┌─────────────────────────────────────────────────────┐
│        APPLICATION ANDROID (Droidkotlin)            │
│                                                     │
│  ┌──────────────┐         ┌──────────────┐        │
│  │ MODE RAPIDE  │         │ MODE AVANCÉ  │        │
│  │   (Local)    │         │   (Cloud)    │        │
│  │              │         │              │        │
│  │ • CameraX    │         │ • CameraX    │        │
│  │ • Analyse    │         │ • Upload     │        │
│  │   couleurs   │         │ • Backend    │        │
│  │ • Résultat   │         │ • Gemini     │        │
│  │   instantané │         │   Vision     │        │
│  └──────────────┘         └──────┬───────┘        │
│                                  │                 │
│  Room Database (historique)      │                 │
└──────────────────────────────────┼─────────────────┘
                                   │
                                   │ HTTPS
                                   │
                    ┌──────────────▼──────────────┐
                    │  BACKEND (NestJS partagé)   │
                    │                             │
                    │  Module Plants              │
                    │    ↓                        │
                    │  Gemini Vision API          │
                    │    ↓                        │
                    │  PostgreSQL                 │
                    └─────────────────────────────┘
```

---

## 🔧 STACK TECHNIQUE

| Composant | Technologie | Pourquoi |
|-----------|-------------|----------|
| **Frontend** | Kotlin + Jetpack Compose | Interface moderne Android |
| **Caméra** | CameraX | Capture photos natives |
| **Architecture** | Clean Architecture (Droidkotlin) | Code organisé |
| **ML Local** | TensorFlow Lite (PlantVillage) | Détection offline |
| **Backend** | NestJS (partagé Projet 1) | Économie ressources |
| **IA Vision** | Google Gemini 1.5 Flash | Gratuit, vision + texte |
| **Base locale** | Room (SQLite) | Historique hors ligne |
| **Base cloud** | PostgreSQL | Stockage détections cloud |

---

## 🌿 MALADIES DÉTECTABLES (MVP)

### **Mode Rapide - TensorFlow Lite (38 classes) :**

**Modèle PlantVillage pré-entraîné :**
- Pomme (4 maladies)
- Tomate (9 maladies + saine)
- Maïs (4 maladies + saine)
- Raisin (4 maladies + saine)
- Pomme de terre (3 maladies + saine)
- Poivron, pêche, fraise, cerise, etc.

**Total : 38 classes détectables offline**

### **Mode Avancé - Gemini Vision :**
- Toutes celles du mode rapide +
- Plantes camerounaises spécifiques :
  - Manioc (mosaïque, cercosporiose)
  - Plantain (cercosporiose noire)
  - Cacao (pourriture brune)
- Recommandations contextualisées Cameroun

---

## 👥 RÉPARTITION DES ÉQUIPES

### **Équipe Frontend (3 élèves)**

**Responsabilités :**
- CameraScreen avec CameraX (capture + preview)
- Tests TensorFlow Lite avec photos réelles
- ResultScreen (affichage résultat)
- HistoryScreen (historique local)
- Comparaison mode rapide vs avancé

**Livrables :**
- Interface caméra fonctionnelle
- Rapport tests TFLite (20+ photos, taux précision)
- Écrans de résultats stylisés
- Document comparatif modes

**Formation nécessaire :**
- CameraX Android (2h)
- TensorFlow Lite concepts (2h)
- Méthodologie tests (1h)

---

### **Équipe Backend (2 élèves)**

**Responsabilités :**
- Module Plants dans NestJS
- Endpoint upload image
- Intégration Gemini Vision API
- Sauvegarde détections PostgreSQL

**Livrables :**
- `POST /api/v1/plants/detect` fonctionnel
- Intégration Gemini Vision
- Base de données des détections

**Formation nécessaire :**
- Upload fichiers NestJS (2h)
- Gemini Vision API (2h)
- Même base que Projet 1

---

### **Équipe Contenu (2 élèves)**

**Responsabilités :**
- Recherche maladies camerounaises
- Rédaction 15 fiches maladies complètes
- Collecte 30 photos test (plantes camerounaises)
- Tests utilisateurs et documentation bugs

**Livrables :**
- 15 fiches maladies (symptômes, traitement, prévention)
- 30+ photos test organisées par maladie
- Document de tests et bugs
- Comparaison précision TFLite vs Gemini

**Formation nécessaire :**
- Recherche phytopathologie (2h)
- Photographie plantes (1h)
- Documentation scientifique (1h)

---

## 📅 PLANNING 3 SEMAINES

### **Semaine 1 : Setup + Base**

**Lundi :**
- Installation outils (partagé avec Projet 1)
- Formation CameraX, TensorFlow Lite, NestJS modules

**Mardi-Mercredi :**
- Frontend : CameraScreen basique
- Backend : Structure module Plants
- Contenu : Recherche 5 premières maladies
- **Encadreur : Intégration TFLite (3h)**

**Jeudi-Vendredi :**
- Frontend : Tests TFLite avec 10 photos
- Backend : Endpoint upload image
- Contenu : Collecter 15 photos test

---

### **Semaine 2 : Développement**

**Lundi-Mardi :**
- Frontend : Tests exhaustifs TFLite (20+ photos)
- Backend : Intégration Gemini Vision
- Contenu : Rédiger 8 fiches maladies

**Mercredi-Jeudi :**
- Frontend : ResultScreen + comparaison modes
- Backend : Parser réponse Gemini, sauvegarder DB
- Contenu : Finaliser 15 fiches

**Vendredi :**
- Frontend : HistoryScreen (Room)
- Intégration complète
- Tests mode hybride (offline vs online)

---

### **Semaine 3 : Finalisation**

**Lundi-Mardi :**
- Tests avec vraies photos de plantes
- Corrections bugs
- Optimisation interface

**Mercredi-Jeudi :**
- Préparation présentation
- Documentation technique
- Répétition démo

**Vendredi :**
- **Présentation JPOE 2026** 🎉

---

## 🤖 MODE RAPIDE - TENSORFLOW LITE

### **Répartition du travail :**

**Encadreur (intégration technique) :**
- Téléchargement modèle PlantVillage pré-entraîné
- Intégration TFLite dans l'app Android
- Code prétraitement images (redimensionnement 224x224)
- Conversion Bitmap → Tensor

**Élèves Frontend (tests et documentation) :**
- Tests avec 30+ photos de plantes
- Documentation précision par type de maladie
- Comparaison TFLite vs Gemini Vision
- Calibration seuils de confiance
- Rapport scientifique des résultats

### **Fonctionnement TensorFlow Lite :**

```kotlin
// Exemple de résultat
Input  : Photo feuille tomate avec taches
Output : [
  "Tomato___Late_blight"   : 85.21%  ← Meilleur résultat
  "Tomato___Early_blight"  : 8.23%
  "Tomato___Leaf_spot"     : 4.21%
  "Tomato___healthy"       : 1.56%
  ...
]

Résultat affiché :
- Plante : Tomate
- Maladie : Mildiou (Late Blight)
- Confiance : 85%
- Temps : 1-2 secondes
```

### **Performance :**
- Temps détection : 1-2 secondes
- Précision moyenne : 75-85%
- Fonctionne hors ligne ✅
- 38 classes détectables

---

## 🤖 MODE AVANCÉ (Gemini Vision)

**Prompt optimisé pour Gemini :**

```typescript
const prompt = `
Tu es un expert en phytopathologie spécialisé cultures africaines.

Analyse cette photo de plante :

1. Plante identifiée (manioc, tomate, maïs, etc.)
2. Maladie détectée (précis ou "plante saine")
3. Confiance 0-100%
4. Symptômes visibles
5. Traitement (produits disponibles Cameroun)
6. Prévention

Réponds en JSON :
{
  "plant": "nom",
  "disease": "nom maladie",
  "confidence": 85,
  "severity": "léger/modéré/grave",
  "symptoms": ["symptôme1", "symptôme2"],
  "treatment": ["traitement1", "traitement2"],
  "prevention": ["conseil1", "conseil2"]
}
`;
```

---

## 📦 LIVRABLES FINAUX

1. ✅ **Application Android** (.apk installable)
2. ✅ **Code source** (GitHub avec documentation)
3. ✅ **Modèle TFLite intégré** (PlantVillage)
4. ✅ **Base de données** (15 maladies + recommandations)
5. ✅ **Module Backend** (intégré au backend Projet 1)
6. ✅ **Rapport tests TFLite** (précision, comparaisons)
7. ✅ **Rapport technique** (architecture hybride expliquée)
8. ✅ **Présentation PowerPoint** (démo 2 modes)

---

## 🎓 COMPÉTENCES ACQUISES

### **Pour tous les élèves :**
- Vision par ordinateur (concepts)
- Architecture hybride (local + cloud)
- IA multimodale (Gemini Vision)
- Agriculture et phytopathologie

### **Équipe Frontend (compétences nouvelles vs Projet 1) :**
- **CameraX** (caméra native Android)
- **TensorFlow Lite** (ML on-device)
- **Tests ML** (méthodologie, documentation scientifique)
- **Analyse comparative** (offline vs online)
- **Permissions runtime**
- **Upload fichiers**

### **Équipe Backend (compétences nouvelles vs Projet 1) :**
- **Gemini Vision API** (images + texte)
- **Upload multipart/form-data**
- **Traitement images côté serveur**
- **Modules NestJS avancés**

### **Équipe Contenu :**
- **Phytopathologie** (maladies plantes)
- **Agriculture camerounaise**
- **Photographie technique**
- **Recherche scientifique**

---

## ⚙️ DIFFÉRENCIATION AVEC PROJET 1

| Aspect | Projet 1 (Personnalité) | Projet 2 (Plantes) |
|--------|------------------------|---------------------|
| **Entrée utilisateur** | Questionnaire texte | Photo caméra |
| **Traitement** | Algorithme scoring | ML vision (TFLite + Gemini) |
| **IA utilisée** | Gemini texte | TensorFlow Lite + Gemini Vision |
| **Mode hors ligne** | Non | Oui (TFLite local) |
| **Compétence clé** | Backend + IA texte | Frontend + ML mobile + IA vision |
| **Domaine** | Psychologie | Agriculture |
| **Précision offline** | N/A | 75-85% (TFLite) |
| **Temps réponse** | 3-5s (online) | 1-2s (offline) + 6-10s (online) |

**Complémentarité technique parfaite !**

---

## ⚠️ DÉFIS ANTICIPÉS

| Défi | Solution |
|------|----------|
| **Qualité photos variables** | Tests avec différentes conditions lumière |
| **TFLite limité plantes camerounaises** | Mode rapide = indication, mode avancé = précision |
| **Upload images lourdes** | Compression JPEG à 80% avant envoi |
| **API Gemini lente** | Indicateur de chargement, timeout 15s |
| **Photos test difficiles** | Utiliser images internet + crédit sources |
| **Intégration TFLite complexe** | Encadreur gère l'intégration technique |

---

## ✅ CRITÈRES DE RÉUSSITE

Le projet est réussi si :

1. ✅ La caméra fonctionne (capture photo)
2. ✅ TensorFlow Lite détecte au moins 10 maladies correctement
3. ✅ Le mode avancé appelle Gemini Vision avec succès
4. ✅ Les résultats s'affichent correctement (2 modes)
5. ✅ L'historique sauvegarde les détections
6. ✅ Rapport comparatif modes (précision, temps)
7. ✅ La démo JPOE impressionne avec les 2 modes
8. ✅ Chaque élève maîtrise sa partie

---

## 🎤 PRÉSENTATION JPOE

**Storytelling recommandé :**

> "Au Cameroun, 70% de la population dépend de l'agriculture. 
> Beaucoup d'agriculteurs perdent leurs récoltes à cause de maladies 
> non détectées à temps.
>
> Notre application propose **2 niveaux d'assistance** :
>
> **MODE RAPIDE** : Pour les zones rurales sans internet.
> Nous avons intégré TensorFlow Lite, un modèle de Machine Learning 
> qui fonctionne directement sur le téléphone. L'agriculteur prend 
> une photo, l'analyse se fait localement en 1-2 secondes.
> Notre équipe a testé le modèle sur 30 photos de plantes 
> camerounaises et documenté une précision de 75-85%.
>
> **MODE AVANCÉ** : Pour un diagnostic précis. La photo est 
> analysée par l'IA Google Gemini Vision qui génère des 
> recommandations personnalisées avec les traitements disponibles 
> au Cameroun. Précision : 90-95%.
>
> Cette **approche hybride** garantit l'accessibilité pour tous, 
> qu'ils aient internet ou non."

**Impact garanti !** 🌟

---

## 🚀 ÉVOLUTIONS FUTURES (POST-JPOE)

Après le JPOE, si le projet continue :

- Entraîner modèle TFLite sur plantes camerounaises (manioc, plantain, cacao)
- 50+ maladies spécifiques au Cameroun
- Géolocalisation (carte des maladies par région)
- Communauté d'agriculteurs
- Chatbot conseil personnalisé
- Reconnaissance de nuisibles (insectes)
- Calendrier agricole intelligent
- Marketplace produits phyto locaux

---

**Document préparé le :** [Date]  
**Version :** MVP 1.0 (Mode Hybride)  
**Contact encadreur :** [Email/Téléphone]
