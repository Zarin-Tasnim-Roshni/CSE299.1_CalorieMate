# CalorieMate: AI-Driven Nutritional Analysis for Bengali Cuisine

**CalorieMate** is a comprehensive nutritional assessment and food recognition tool designed specifically for the **Bangladeshi population**. Developed as part of a Junior Design Course at **North South University**, the app utilizes **deep learning** to identify local dishes and provide personalized dietary guidance.

## 🚀 Features

*   **Bengali Food Recognition:** Uses a fine-tuned **MobileNet_V2** model to identify **58 classes** of Bengali cuisine, including unique items like "Bhorta," "Chitoi Pitha," and "Bhapa Pitha".
*   **Nutrient Evaluation:** Provides detailed nutritional values (Protein, Fat, Carbohydrates, and Minerals) per **100-gram sample** for identified dishes.
*   **Health Calculators:**
    *   **BMI (Body Mass Index) Calculator:** Helps users assess their weight category and potential health risks.
    *   **BMR (Basal Metabolic Rate) Calculator:** Estimates daily calorie needs based on age, height, weight, gender, and activity level.
*   **Macro-Nutrient Distribution:** Breaks down daily calorie targets into specific protein, carbohydrate, and fat goals based on user-selected meal plans (e.g., **High Protein, Low Carb, or Low Fat**).
*   **Offline Experience:** The app is designed for **hassle-free, offline use**, ensuring accessibility even without an internet connection.

## 🧠 System Architecture & Implementation

### Deep Learning Model
The core recognition system is built on **MobileNet_V2**, a lightweight CNN architecture developed by Google that offers **low-latency** performance on mobile devices. The model was fine-tuned using the **BanglaFood_V2** dataset, with the final fully-connected layer replaced to accommodate the 58 specific food classes.

### Dataset: BanglaFood_V2
To ensure high accuracy, the project curated a comprehensive dataset by:
*   Merging multiple sources from Kaggle, including Indian and Fast Food datasets.
*   Collecting **real-life data** from social media (Instagram, Facebook) and manual photography.
*   Expanding the dataset from an original 7,732 images to **31,342 total images**.

### Training Details
*   **Framework:** TensorFlow/Keras.
*   **Optimizer:** Adam optimizer with categorical cross-entropy loss.
*   **Accuracy:** Achieved over **98% accuracy** on the test set.

## 📊 Results
The model demonstrates high confidence in predicting common dishes such as **Kacchi Biryani, Fuchka, and Khichuri**. While real-life performance can vary due to lighting and framing, the system remains a robust tool for bridging the gap between technology and dietary awareness in Bangladesh.

## 🛠️ Technologies Used
*   **Language:** Python (for model training).
*   **Deep Learning:** MobileNet_V2, CNN, Transfer Learning.
*   **App Development:** Android-based environment (implemented as an offline utility).

## 👥 Contributors
*   Rafin Hassan
*   Zarin Tasnim Roshni
*   Mehrab-E-Rashid Khan
*   Mushfiq Uzzaman Suharto

**Course Faculty:** Nova Ahmed, North South University

---
*Note: This project was submitted on 6th November 2023 for the CSE299.1 Junior Design Course.*
