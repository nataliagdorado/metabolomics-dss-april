# 🧬 Metabolomics Decision Support System (DSS) for LC-MS Annotation

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![SQLite](https://img.shields.io/badge/SQLite-Database-blue?style=for-the-badge&logo=sqlite)
![Drools](https://img.shields.io/badge/Drools-Rule_Engine-red?style=for-the-badge)
![Data Science](https://img.shields.io/badge/Data_Science-Fuzzy_Logic-success?style=for-the-badge)
![Bioinformatics](https://img.shields.io/badge/Bioinformatics-LC--MS-lightgrey?style=for-the-badge)

## 📌 Executive Summary
This repository contains a rule-based **Decision Support System (DSS)** designed to process and annotate Liquid Chromatography-Mass Spectrometry (LC-MS) data.The system transforms raw detected signals (peaks) into ranked metabolite identificated.

The project showcases a strong separation of concerns, decoupling deterministic data processing from knowledge-based expert reasoning. It demonstrates proficiency in **Relational Databases (SQL)**, **Algorithm Design**, and **Software Architecture**.

## 🎯 Key Competencies Demonstrated
* **Data Integration & SQL:** Querying and management of chemical compound libraries using JDBC and SQLite, mapping exact masses to complex database schemas.
* **Knowledge-Based Engineering:** Implementation of an expert rule engine (Drools) applying **Fuzzy Logic** to evaluate adduct hierarchies and complex biochemical patterns.
* **Modular Software Architecture:** H
* **Scoring Systems:** Algorithmic integration of multiple evidence sources (mass accuracy, adduct relative intensity, and expected elution order via LogP) into a transparent, weighted final score.

---

## ⚙️ Core Architecture & Execution Flow


### Stage 1 & 2: Data-Driven Deconvolution
* Evaluates raw LC-MS peaks and groups them into unified features using RT tolerance windows.
* Calculates theoretical neutral masses through algorithmic deconvolution of detected signals.

### Stage 3: Database Mapping (SQL)
* Interfaces with a localized SQLite relational database.
* Executes parameterized SQL queries to map calculated neutral masses to potential metabolite candidates within strict scientific tolerances 

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
* **Rule Engine:** Drools (Rule Units, `.drl` files for business logic)
* **Build / Dependency Management:** Maven
* **Testing:** JUnit 4 


