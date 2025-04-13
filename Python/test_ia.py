import pandas as pd
import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Embedding, LSTM, Dense, Dropout
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sqlalchemy import create_engine
import re

def limpar_texto(texto):
    texto = texto.lower()
    texto = re.sub(r'[^a-zA-Zà-úÀ-Ú0-9\s]', '', texto)
    texto = re.sub(r'\s+', ' ', texto).strip()
    return texto

engine = create_engine("mysql+mysqlconnector://BD24336:BD24336@regulus.cotuca.unicamp.br/bd24336")
df = pd.read_sql("SELECT phrase, classification FROM messages", con=engine)
df.columns = ['messagem', 'categoria']

df['messagem'] = df['messagem'].astype(str).apply(limpar_texto)

print("\n--- Distribuição das categorias ---")
print(df['categoria'].value_counts(), "\n")

X = df['messagem']
y = df['categoria']

label = LabelEncoder()
y_encoded = label.fit_transform(y)

X_train, X_test, y_train, y_test = train_test_split(X, y_encoded, test_size=0.2, random_state=42)
#tokenização padroniza em 100 palavras e trasnforma as frases em numeros, para a separação
token = tf.keras.preprocessing.text.Tokenizer(num_words=10000, oov_token="<OOV>")
token.fit_on_texts(X_train)
X_train_seq = token.texts_to_sequences(X_train)
X_test_seq = token.texts_to_sequences(X_test)

X_train_pad = tf.keras.preprocessing.sequence.pad_sequences(X_train_seq, maxlen=100)
X_test_pad = tf.keras.preprocessing.sequence.pad_sequences(X_test_seq, maxlen=100)

model = Sequential([
    Embedding(input_dim=10000, output_dim=128, input_length=100),
    LSTM(128, return_sequences=True),
    Dropout(0.5),
    LSTM(64),
    Dropout(0.5),
    Dense(64, activation='relu'),
    Dense(len(set(y_encoded)), activation='softmax')
])

model.compile(loss='sparse_categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
model.summary()

model.fit(X_train_pad, y_train, epochs=20, validation_data=(X_test_pad, y_test))

model.save("modelo_classificacao.h5")

def testar_frase(frase):
    frase = limpar_texto(frase)
    frase_seq = token.texts_to_sequences([frase])
    frase_pad = tf.keras.preprocessing.sequence.pad_sequences(frase_seq, maxlen=100)
    predicao = model.predict(frase_pad)
    categoria = label.inverse_transform([tf.argmax(predicao[0])])[0]
    print(f'\nA frase: "{frase}" foi classificada como: {categoria}\n')

while True:
    frase = input("Digite uma frase para classificar (ou 'sair'): ")
    if frase.strip().lower() == 'sair':
        print("Saindo...")
        break
    testar_frase(frase)