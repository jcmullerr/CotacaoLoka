package br.feevale.cotacaoloka;

public class Cotacao {
    private long Id;
    private double Dolar;
    private double Libra;
    private double Peso_Argentino;
    private String Data_Cotacao;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public double getDolar() {
        return Dolar;
    }

    public void setDolar(double dolar) {
        Dolar = dolar;
    }

    public double getLibra() {
        return Libra;
    }

    public void setLibra(double libra) {
        Libra = libra;
    }

    public double getPeso_Argentino() {
        return Peso_Argentino;
    }

    public void setPeso_Argentino(double peso_Argentino) {
        Peso_Argentino = peso_Argentino;
    }
    public String getDataCotacao() {
        return Data_Cotacao;
    }

    public void setDataCotacao(String data_Cotacao) {
        this.Data_Cotacao = data_Cotacao;
    }
}
