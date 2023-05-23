package game.entity.item;

/*Denunciar fake news: esse item oferece a ação de denunciar qualquer fake
news em volta do jogador, eliminando-as (se houver) nas 8 (oito) posições
adjacentes à posição do jogador.
b. Fugir: esse item permite que o jogador mude para qualquer outra posição do
tabuleiro.
c. Ler uma notícia real: esse item permite que o jogador elimine uma fake news
qualquer presente no tabuleiro.
d. Ouvir um boato: esse item infelizmente é feito para atrapalhar os jogadores.
Caso um jogador o armazene, no próximo turno o movimento desse jogador é
realizado de forma aleatória.*/

public enum ItemMessage {
    REPORT_FAKE_NEWS("Denunciar fake news"),
    RUN_AWAY("Fugir"),
    READ_A_REAL_NEWS("Ler uma notícia real"),
    HEAR_A_RUMOR("Ouvir um boato");
    
    private final String message;
    
    private ItemMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
