import openai

client = openai.OpenAI(api_key="Code openai")

def enviar_mensagem(mensagem, lista_mensagens):
    lista_mensagens.append({"role": "user", "content": mensagem})

    resposta = client.chat.completions.create(
        model="gpt-4-turbo",
        messages=lista_mensagens
    )

    resposta_mensagem = resposta.choices[0].message.content
    lista_mensagens.append({"role": "assistant", "content": resposta_mensagem})
    
    return resposta_mensagem

lista_mensagens = []

while True:
    texto = input("Escreva sua mensagem aqui: ")

    if texto.lower() == "sair":
        break
    else:
        resposta = enviar_mensagem(texto, lista_mensagens)
        print("Chatbot:", resposta)