package br.com.up.upark.upark;

import android.content.Context;
import android.location.Geocoder;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MARIA LINA on 06/10/2016.
 */
@SuppressWarnings("serial")
public class Estacionamento implements Parcelable{
    public String Nome;
    public String Email;
    public String Senha;
    public String Endereco;
    public LatLng LatLng;
    public int NumeroVagas;
    public String CNPJ;
    public double Preco;
    public int EstacionamentoId;
    public double Credito;

    public double getCredito() {
        return Credito;
    }

    public void setCredito(double credito) {
        Credito = credito;
    }

    protected Estacionamento(Parcel in) {
        Nome = in.readString();
        Email = in.readString();
        Senha = in.readString();
        Endereco = in.readString();
        LatLng = in.readParcelable(com.google.android.gms.maps.model.LatLng.class.getClassLoader());
        NumeroVagas = in.readInt();
        CNPJ = in.readString();
        Preco = in.readDouble();
        EstacionamentoId = in.readInt();
        HorarioFuncio = in.readString();
    }

    public static final Creator<Estacionamento> CREATOR = new Creator<Estacionamento>() {
        @Override
        public Estacionamento createFromParcel(Parcel in) {
            return new Estacionamento(in);
        }

        @Override
        public Estacionamento[] newArray(int size) {
            return new Estacionamento[size];
        }
    };

    public Estacionamento() {

    }

    public int getEstacionamentoId() {
        return EstacionamentoId;
    }

    public void setEstacionamentoId(int estacionamentoId) {
        EstacionamentoId = estacionamentoId;
    }

    public com.google.android.gms.maps.model.LatLng getLatLng() {
        return LatLng;
    }

    public void setLatLng(com.google.android.gms.maps.model.LatLng latLng) {
        LatLng = latLng;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public int getNumeroVagas() {
        return NumeroVagas;
    }

    public void setNumeroVagas(int numeroVagas) {
        NumeroVagas = numeroVagas;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public double getPreco() {
        return Preco;
    }

    public void setPreco(double preco) {
        Preco = preco;
    }

    public String getHorarioFuncio() {
        return HorarioFuncio;
    }

    public void setHorarioFuncio(String horarioFuncio) {
        HorarioFuncio = horarioFuncio;
    }

    public String HorarioFuncio;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Nome);
        dest.writeString(Email);
        dest.writeString(Senha);
        dest.writeString(Endereco);
        dest.writeParcelable(LatLng, flags);
        dest.writeInt(NumeroVagas);
        dest.writeString(CNPJ);
        dest.writeDouble(Preco);
        dest.writeInt(EstacionamentoId);
        dest.writeString(HorarioFuncio);
    }
}
