package vee.apps.gitscommerce.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by binaryvi on 13/05/2016.
 */
public class DetailData {

    @SerializedName("nama")
    private String nama;

    @SerializedName("harga")
    private String harga;

    @SerializedName("diskon")
    private String diskon;

    @SerializedName("deskripsi")
    private String deskripsi;

    @SerializedName("spesifikasi")
    private String spesifikasi;

    @SerializedName("ukuran")
    ArrayList<DetailUkuran> listUkuran = new ArrayList<>();

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public ArrayList<DetailUkuran> getListUkuran() {
        return listUkuran;
    }

    public void setListUkuran(ArrayList<DetailUkuran> listUkuran) {
        this.listUkuran = listUkuran;
    }

    public String getSpesifikasi() {
        return spesifikasi;
    }

    public void setSpesifikasi(String spesifikasi) {
        this.spesifikasi = spesifikasi;
    }
}
