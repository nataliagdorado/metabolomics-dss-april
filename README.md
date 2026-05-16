# 🧬 Metabolomics Decision Support System (DSS) for LC-MS Annotation

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![SQLite](https://img.shields.io/badge/SQLite-Database-blue?style=for-the-badge&logo=sqlite)
![Drools](https://img.shields.io/badge/Drools-Rule_Engine-red?style=for-the-badge)
![Data Science](https://img.shields.io/badge/Data_Science-Fuzzy_Logic-success?style=for-the-badge)
![Bioinformatics](https://img.shields.io/badge/Bioinformatics-LC--MS-lightgrey?style=for-the-badge)

## 📌 Executive Summary
This repository contains a fully automated, rule-based **Decision Support System (DSS)** designed to process and annotate Liquid Chromatography-Mass Spectrometry (LC-MS) data. By bridging analytical chemistry and data science, the system transforms raw detected signals (features) into ranked, highly confident metabolite identifications.

The project showcases a strong separation of concerns, decoupling deterministic data processing from knowledge-based expert reasoning. It demonstrates proficiency in **Relational Databases (SQL)**, **Algorithm Design**, and **Software Architecture**, specifically tailored for bioinformatics and cheminformatics workflows.

## 🎯 Key Competencies Demonstrated
* **Data Integration & SQL:** Robust querying and management of chemical compound libraries using JDBC and SQLite, mapping exact masses to complex database schemas.
* **Knowledge-Based Engineering:** Implementation of an expert rule engine (Drools) applying **Fuzzy Logic** to evaluate adduct hierarchies and complex biochemical patterns.
* **Modular Software Architecture:** Highly decoupled, 6-stage pipeline designed for extreme flexibility. The system allows for seamless modifications, such as tweaking RT (Retention Time) grouping windows, adjusting fuzzy scoring functions, introducing new adducts, or modifying compound behavior logic during chromatography without breaking the core execution.
* **Explainable AI / Scoring Systems:** Algorithmic integration of multiple evidence sources (mass accuracy, adduct relative intensity, and expected elution order via LogP) into a transparent, weighted final score.

---

## ⚙️ Core Architecture & Execution Flow


### Stage 1 & 2: Data-Driven Deconvolution
* Evaluates raw LC-MS peaks and groups them into unified features using strict RT tolerance windows.
* Calculates theoretical neutral masses through algorithmic deconvolution of detected signals.

### Stage 3: Database Mapping (SQL)
* Interfaces with a localized SQLite relational database.
* Executes parameterized SQL queries to map calculated neutral masses to potential metabolite candidates within strict scientific tolerances (e.g., 0.05 Da).

### Stage 4: Adduct Pattern Evaluation (Fuzzy Logic)
* Ingests putative annotations and applies Drools-based expert rules.

### Stage 5: Contextual Elution Reasoning
* Evaluates the physical chromatography behavior of compounds.

### Stage 6: Final Integration & Ranking
* Aggregates partial scores (Adduct + RT) using a configurable weighted average.
* Outputs a deterministic, ranked, and fully explainable list of putative annotations.

---

## 🛠️ Technical Stack
* **Core Language:** Java 21
* **Database / Persistence:** SQLite, JDBC (Raw SQL query optimization)
* **Rule Engine:** Drools 10 (Rule Units, `.drl` files for business logic)
* **Build / Dependency Management:** Maven
* **Testing:** JUnit 4 (Comprehensive validation of rule logic and state modifications)

## 🚀 Getting Started

### Prerequisites
* JDK 21 installed and configured.
* Maven 3.8+ installed.
* `metabolite.db` properly seeded with reference compounds located in the project root.

mvn exec:java -Dexec.mainClass="pipeline.ExplanaibleMain"
