# Persome - Test de Personnalité MBTI avec IA

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.x-7F52FF.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-API%2024+-3DDC84.svg?style=flat&logo=android)](https://developer.android.com)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Application mobile Android de test de personnalité MBTI utilisant l'intelligence artificielle Google Gemini pour générer des descriptions personnalisées et des recommandations de carrières adaptées au Cameroun.

**Projet JPOE 2026** - "La créativité à l'ère de l'Intelligence Artificielle : de l'artisanat à la haute technologie"

---

## 🎯 Objectif

Développer une application Android qui permet aux utilisateurs de :
- Répondre à 30 questions MBTI (Myers-Briggs Type Indicator)
- Découvrir leur type de personnalité parmi 16 types possibles (INTJ, ENFP, etc.)
- Obtenir une description personnalisée générée par Google Gemini (~200 mots)
- Recevoir des recommandations de carrières adaptées au contexte camerounais
- Consulter l'historique local de leurs tests

---

## 👥 Équipe Pédagogique

- **Encadreur** : Assistant technique et pédagogique
- **Élèves** : 5 élèves de Terminale TI (débutants en programmation)
- **Durée** : 3 semaines
- **Niveau** : JavaScript basique uniquement (pas d'OOP, pas d'async/await)

---

## 🏗️ Architecture Technique

### Stack Technique
- **Frontend** : Kotlin + Jetpack Compose (template DroidKotlin)
- **Architecture** : Clean Architecture (Presentation / Domain / Data)
- **Backend** : NestJS + TypeScript (partagé avec projet PlantDoc)
- **Base de données** : PostgreSQL (Digital Ocean)
- **IA** : Google Gemini API (gratuit, 60 requêtes/minute)

### Architecture Clean
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Presentation  │    │     Domain      │    │      Data       │
│                 │    │                 │    │                 │
│  • Compose UI   │◄──►│  • Use Cases    │◄──►│  • Repositories │
│  • ViewModels   │    │  • Entities     │    │  • Data Sources │
│  • Navigation   │    │  • MBTI Logic   │    │  • API Services │
│  • Questionnaire │    │  • Repositories │    │  • Local Storage│
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

---

## 📱 Fonctionnalités

### ✅ Fonctionnalités MVP
1. **Questionnaire MBTI** (30 questions)
   - Questions sur les préférences personnelles
   - Choix binaire (A ou B)
   - Progression visuelle

2. **Calcul du type MBTI**
   - Algorithme de scoring sur 4 dimensions
   - E/I (Q1-Q10), S/N (Q11-Q17), T/F (Q18-Q21), J/P (Q22-Q30)
   - Résultat parmi 16 types possibles

3. **Description personnalisée par IA**
   - Google Gemini génère un texte unique
   - Forces, axes de développement, carrières au Cameroun
   - 200 mots environ en français

4. **Affichage des résultats**
   - Type de personnalité (ex: INTJ)
   - Description complète générée par l'IA
   - Recommandations de carrières adaptées

5. **Historique local**
   - Sauvegarde des tests passés
   - Consultation des résultats précédents

### ❌ Hors MVP (pour plus tard)
- Comptes utilisateurs / authentification
- Synchronisation cloud
- Partage sur réseaux sociaux
- Chatbot conversationnel
- Statistiques avancées

---

## 🔧 Installation et Configuration

### Prérequis
- **Android Studio** Hedgehog | 2023.1.1 ou plus
- **JDK** 17 ou plus
- **Android SDK** API 24 minimum, API 34+ recommandé
- **Git** pour le contrôle de version

### Installation

1. **Cloner le projet**
   ```bash
   git clone https://github.com/votre-org/jpoe2026.git
   cd jpoe2026/persome
   ```

2. **Ouvrir dans Android Studio**
   - File → Open → Sélectionner le dossier `persome`
   - Attendre la synchronisation Gradle
   - Résoudre les problèmes de SDK ou dépendances

3. **Configurer l'API**
   - Créer `local.properties` dans le dossier racine
   ```properties
   # Configuration API Persome
   api.base.url="https://votre-backend.com/"
   api.gemini.key="votre_cle_gemini_api"
   
   # Configuration build
   persome.version.code=1
   persome.version.name=1.0.0
   ```

4. **Build et exécution**
   ```bash
   ./gradlew assembleDebug
   ./gradlew installDebug
   ```

---

## 📊 Algorithme MBTI

### Dimensions évaluées
1. **E/I** (Extraversion/Introversion) - Questions 1-10
2. **S/N** (Sensation/Intuition) - Questions 11-17
3. **T/F** (Pensée/Feeling) - Questions 18-21
4. **J/P** (Judgement/Perception) - Questions 22-30

### Calcul du score
- Chaque réponse A ou B → lettre correspondante
- Compter les lettres par dimension
- Lettre majoritaire gagne dans chaque dimension
- Combiner les 4 lettres → type final (ex: INTJ)

### Exemple de résultat
```json
{
  "type": "INTJ",
  "description": "En tant qu'INTJ, vous êtes...",
  "careers": ["Ingénieur logiciel", "Analyste système", "Chef de projet"],
  "strengths": ["Vision stratégique", "Résolution de problèmes"],
  "development_areas": ["Communication", "Travail d'équipe"]
}
```

---

## 🔌 API Backend

### Endpoints disponibles

#### GET /api/v1/personality/questions
Retourne les 30 questions MBTI
```json
{
  "questions": [
    {
      "id": 1,
      "text": "Préférez-vous passer du temps avec des gens ou seul ?",
      "option_a": "Avec les gens",
      "option_b": "Seul",
      "dimension": "E/I"
    }
  ]
}
```

#### POST /api/v1/personality/submit
Reçoit les réponses et retourne le résultat MBTI
```json
// Request
{
  "responses": ["A", "B", "A", ...] // 30 réponses
}

// Response
{
  "type": "ENFP",
  "description": "Texte généré par Gemini...",
  "careers": ["Consultant", "Enseignant", "Artiste"],
  "confidence": 0.92
}
```

---

## 🏛️ Structure du Projet

```
persome/
├── app/                          # Module principal
│   ├── src/main/java/
│   │   ├── presentation/         # UI Compose, ViewModels
│   │   │   ├── questionnaire/     # Écran questionnaire
│   │   │   ├── result/           # Écran résultats
│   │   │   └── history/          # Écran historique
│   │   ├── domain/               # Logique métier
│   │   │   ├── model/            # Modèles MBTI
│   │   │   ├── repository/       # Interfaces
│   │   │   └── usecase/          # Cas d'usage
│   │   └── data/                 # Données et API
│   │       ├── remote/           # API backend
│   │       ├── local/            # Stockage local
│   │       └── repository/        # Implémentations
├── buildSrc/                     # Configuration build
├── gradle/                       # Wrapper Gradle
└── docs/                         # Documentation
```

---

## 👨‍💻 Répartition des Équipes

### Équipe Backend (2 élèves)
**Responsabilités :**
- Créer les 30 questions MBTI en TypeScript
- Implémenter les endpoints API (templates fournis)
- Tester avec Postman
- Intégrer Google Gemini API

**Livrables :**
- Fichier `questions.data.ts` avec 30 questions
- Endpoint `GET /api/v1/personality/questions` fonctionnel
- Endpoint `POST /api/v1/personality/submit` fonctionnel

### Équipe Frontend (2 élèves)
**Responsabilités :**
- Modifier l'interface Compose (couleurs, textes, layouts)
- Créer QuestionCard et ResultScreen
- Implémenter la navigation entre écrans
- Tester sur smartphones

**Livrables :**
- QuestionnaireScreen stylisé et fonctionnel
- ResultScreen avec affichage du type MBTI
- Navigation complète entre écrans

### Équipe Contenu (1 élève)
**Responsabilités :**
- Rédiger les 30 questions MBTI
- Créer 16 descriptions de base (fallback)
- Tester l'application et documenter les bugs
- Valider la pertinence des carrières camerounaises

**Livrables :**
- Document avec 30 questions validées
- 16 descriptions MBTI (200 mots chacune)
- Liste de bugs trouvés et corrections

---

## 📅 Planning 3 Semaines

### Semaine 1 : Setup + Base
- **Lundi** : Installation Android Studio + Git, formations TypeScript/Kotlin
- **Mardi-Mercredi** : Backend (questions), Frontend (exploration Droidkotlin), Contenu (recherche MBTI)
- **Jeudi-Vendredi** : Backend (endpoint GET), Frontend (QuestionnaireScreen basique), Contenu (finaliser questions)

### Semaine 2 : Développement
- **Lundi-Mardi** : Backend (endpoint POST + algorithme), Frontend (affichage questions + réponses)
- **Mercredi-Jeudi** : Backend (intégration Gemini), Frontend (ResultScreen), Contenu (descriptions)
- **Vendredi** : Intégration complète, tests ensemble, corrections

### Semaine 3 : Finalisation
- **Lundi-Mardi** : Tests sur smartphones réels, corrections bugs
- **Mercredi-Jeudi** : Préparation présentation PowerPoint, documentation
- **Vendredi** : **Présentation JPOE 2026** 🎉

---

## 🧪 Tests

### Exécuter les tests
```bash
# Tests unitaires
./gradlew test

# Tests UI
./gradlew connectedAndroidTest

# Qualité de code
./gradlew detekt ktlintCheck
```

### Tests manuels recommandés
- Test avec 30 réponses A → type EEEE
- Test avec 30 réponses B → type IIII
- Test avec réponses mixtes → type varié
- Test sans connexion internet (mode dégradé)
- Test rotation écran pendant questionnaire

---

## ⚠️ Contraintes et Solutions

| Contrainte | Solution |
|------------|----------|
| **Pas d'internet à l'école** | Travail hors ligne, installation via USB, tests API à domicile |
| **Élèves débutants** | Templates de code fournis, pair programming, formation progressive |
| **Temps limité (3 semaines)** | Focus MVP, fonctionnalités essentielles uniquement |
| **Budget limité** | Solutions 100% gratuites (Gemini free tier, open source) |

---

## ✅ Critères de Réussite

Le projet est réussi si :
1. ✅ L'application compile et s'installe sur Android
2. ✅ Les 30 questions s'affichent correctement
3. ✅ Le calcul MBTI fonctionne (résultat parmi 16 types)
4. ✅ Gemini génère une description (même basique)
5. ✅ L'interface est présentable pour JPOE
6. ✅ La démo fonctionne sans crash
7. ✅ Chaque élève comprend son rôle et ce qu'il a appris

---

## 🚀 Évolutions Futures (Post-JPOE)

- Comptes utilisateurs avec authentification
- Statistiques des types MBTI au Cameroun
- Comparaison de compatibilité entre types
- Chatbot pour questions personnalisées
- Publication sur Google Play Store
- Partage des résultats sur réseaux sociaux
- Plus de langues (anglais, langues camerounaises)

---

## 📞 Support et Documentation

- **Documentation technique** : `docs/` folder
- **Rapport de projet** : `docs/rapport.md`
- **Présentation JPOE** : `docs/presentation.pptx`
- **Issues** : GitHub Issues du projet

---

## 📄 License

Ce projet est sous license MIT - voir le fichier [LICENSE](LICENSE) pour détails.

---

**Projet réalisé dans le cadre du JPOE 2026 par les élèves de Terminale TI**
**Encadrement technique et pédagogique spécialisé**

**🌟 Si ce projet vous aide, pensez à donner une étoile !**
