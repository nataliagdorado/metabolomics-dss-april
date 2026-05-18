# 🧬 Metabolomics Decision Support System (DSS) for LC-MS Annotation

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![SQLite](https://img.shields.io/badge/SQLite-Database-blue?style=for-the-badge&logo=sqlite)
![Drools](https://img.shields.io/badge/Drools-Rule_Engine-red?style=for-the-badge)
![Data Science](https://img.shields.io/badge/Data_Science-Fuzzy_Logic-success?style=for-the-badge)
![Bioinformatics](https://img.shields.io/badge/Bioinformatics-LC--MS-lightgrey?style=for-the-badge)

## 📌 Executive Summary
This repository contains a rule-based **Decision Support System (DSS)** designed to process and annotate Liquid Chromatography-Mass Spectrometry (LC-MS) data.The system transforms raw detected signals (peaks) into ranked metabolite identificated.

The project showcases a strong separation of concerns, decoupling deterministic data processing from knowledge-based expert reasoning. It demonstrates proficiency in **Relational Databases (SQL)**, **Algorithm Design**, and **Software Architecture**.

## 🎯 Key Competencies
* **Data Integration & SQL:** Querying and management of chemical compound libraries using JDBC and SQLite, mapping exact masses to complex database schemas.
* **Knowledge-Based Engineering:** Implementation of an expert rule engine (Drools) applying **Fuzzy Logic** to evaluate adduct hierarchies and complex biochemical patterns.
* **Modular Software Architecture:** H
* **Scoring Systems:** Algorithmic integration of multiple evidence sources (mass accuracy, adduct relative intensity, and expected elution order via LogP) into a transparent, weighted final score.


## 📁 Project Organization

Below is the architecture and file structure of the expert decision support system:

```text
metabolomics-dss-april/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── adduct/                 # Chemical modeling of adducts
│   │   │   │   ├── Adduct.java
│   │   │   │   └── AdductList.java
│   │   │   ├── dss/                    # Core Decision Support System (Pipeline)
│   │   │   │   ├── Annotation.java
│   │   │   │   ├── Compound.java
│   │   │   │   ├── DatabaseManager.java
│   │   │   │   ├── Feature.java
│   │   │   │   ├── FeatureUnit.java
│   │   │   │   ├── LabelledAdductFeature.java
│   │   │   │   ├── LabelledPeak.java
│   │   │   │   ├── ScoredAnnotation.java
│   │   │   │   ├── Stage1DeconvolutionService.java
│   │   │   │   ├── Stage2NeutralMassService.java
│   │   │   │   ├── Stage3AnnotationService.java
│   │   │   │   ├── Stage4Unit.java
│   │   │   │   ├── Stage5Unit.java
│   │   │   │   └── Stage6RankingService.java
│   │   │   ├── lipid/                  # Raw spectrometry data representation
│   │   │   │   └── Peak.java
│   │   │   └── main/                   # Application entry point
│   │   │       └── App.java
│   │   └── resources/                  # Configuration files, database, and rules
│   │       ├── dss/
│   │       │   ├── Stage4Unit.drl     # Drools 8 rules for Adduct Pattern Scoring
│   │       │   └── Stage5Unit.drl     # Drools 8 rules for Retention Time Scoring
│   │       └── metabolite.db           # Local SQLite database with theoretical compounds
│   └── test/                           # Unit and integration test suite
│       ├── java/
│       │   ├── adduct/
│       │   │   └── AdductTest.java
│       │   └── dss/
│       │       ├── RuleTestS1and2.java
│       │       ├── RuleTestS5and6.java
│       │       ├── RuleTestStage3.java
│       │       └── RuleTestStage4.java
│       └── resources/
│           └── logback-test.xml
├── peak-feature-30.csv                 # Experimental peaks dataset for testing
└── pom.xml                             # Maven dependency configuration file
```

## 🛠️ Pipeline Modules Description

The core of the system is located under the `src/main/java/dss` package and executes sequentially through **6 modular stages**:

* ### 🔹 Stage 1: Deconvolution (`Stage1DeconvolutionService`)
  * **Responsibility:** Groups raw mass spectrometry peaks (`lipid.Peak`) into coherent molecular assemblies called `Features` based on the proximity of their experimental Retention Times (RT).

* ### 🔹 Stage 2: Neutral Mass Search (`Stage2NeutralMassService`)
  * **Responsibility:** Evaluates chemical adduct hypotheses (`adduct.Adduct`) for the peaks grouped within a single Feature. It infers candidate theoretical neutral monoisotopic masses and flags matching entries as `LabelledPeak` instances.

* ### 🔹 Stage 3: Compound Annotation (`Stage3AnnotationService`)
  * **Responsibility:** Establishes a connection with the local relational database (`metabolite.db`) via the `DatabaseManager`. It queries for candidate compounds whose theoretical monoisotopic mass falls within the defined tolerance range (PPM or Da) of the mass inferred in Stage 2.

* ### 🔹 Stage 4: Adduct Pattern Scoring (`Stage4Unit` + `Stage4Unit.drl`)
  * **Responsibility:** Acts as the first expert-knowledge-based filter. It leverages a **Drools 8 (Rule Units)** engine to evaluate the plausibility of detected adduct intensity distributions against known theoretical ionization profiles using fuzzy logic metrics (*Fuzzy Scoring*).

* ### 🔹 Stage 5: Retention Time Scoring (`Stage5Unit` + `Stage5Unit.drl`)
  * **Responsibility:** Acts as the second expert-knowledge-based filter. It models chromatographic behavior (e.g., Reverse Phase hydrophobic trends via logP values) by cross-matching candidates in pairs within a reactive memory space (`DataStore`) using declarative Drools rules.

* ### 🔹 Stage 6: Ranking (`Stage6RankingService`)
  * **Responsibility:** Final algorithmic aggregation layer. It integrates the partial scoring vectors from Stages 4 and 5 by computing a dynamic weighted average determined by the total volume of supporting evidence collected, yielding an optimized list sorted from highest to lowest confidence for the user.
 

## 🛠️ Technical Stack
* **Core Language:** Java 21
* **Database / Persistence:** SQLite, JDBC (Raw SQL query optimization)
* **Rule Engine:** Drools 8 (Rule Units, `.drl` files for business logic)
* **Build / Dependency Management:** Maven
* **Testing:** JUnit 4 


