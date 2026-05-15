# Metabolomics DSS: Automated Metabolite Annotation Pipeline

## 🧪 Project Overview
This project implements a Decision Support System (DSS) designed for the automated annotation of metabolites in Liquid Chromatography-Mass Spectrometry (LC-MS) data. The system processes raw features and, through a modular 6-stage pipeline, assigns chemical identities with confidence levels calculated using expert domain knowledge.

## 🚀 System Architecture (Pipeline)

The system follows a sequential workflow designed to prune the search space and increase annotation precision:

1.  **Stage 1 & 2: Signal Processing**: Receives raw features and calculates theoretical neutral masses through deconvolution.
2.  **Stage 3: Database Mapping**: Queries a SQLite database for potential candidates based on exact mass (default tolerance of 0.05 Da).
3.  **Stage 4: Adduct Scoring**: Evaluates the likelihood of detected adducts using **Fuzzy Logic** implemented in Drools. Mass deviations are mapped to a trapezoidal membership function to assign initial scores.
4.  **Stage 5: Contextual Reasoning (Elution Order)**: Employs expert rules to validate candidates by comparing their hydrophobicity (LogP) with the observed Retention Time (RT).
5.  **Stage 6: Final Ranking**: Integrates individual scores (Adduct + RT) via weighted averaging to generate the final ranked list of identified metabolites.

## 🛠️ Tech Stack
* **Java 21**: Core application logic and service orchestration.
* **Drools 10 (Rule Units)**: Business rule engine for expert reasoning and contextual scoring.
* **SQLite & JDBC**: Persistence and querying of the metabolite reference library.
* **Maven**: Dependency management and lifecycle orchestration (crucial for Drools code generation).
