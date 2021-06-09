# Bangkit Final Capstone Project: Resume Classification and Job Recommender App

This is B21-CAP0110 Team's Final Project

Our team: <br />
Abraham Raja Swara Darwanto - M0101064 <br />
Fathia Amira Nuramalia - M0101067 <br />
I Made Adi Susilayasa - C0141368 <br />
Karlina Surya Witanto - C0141346 <br />
Luthfi Daffa Prabowo - A1901915 <br />
Villa Mukti Indriyanto - A1901910 <br />

This application connects job seekers and job providers, by simply entering a resume, <br />
the job vacancies that match the resume will be displayed, and applicants can easily apply. <br />
We hope that this application can help job seekers to find the job they want. <br />

We use CNN-LSTM algorithm with 100d GloVe pretrained words embedding for the machine <br />
learning model.

The dataset could be downloaded from here: https://bit.ly/3vKsmSH

### Project Documentation <br />
* Download the dataset, pre-process the data in colab or in your local notebook.
* Shuffle and split the dataset to training and validation data.
* Process the data (tokenizing, padding).
* Train the model.
* Evaluate the model.
* Save the model to HDF5 format.
* Deploy saved model on GCP by using by using Cloud Run
* Build REST-API to integrate between ML model and android application.
