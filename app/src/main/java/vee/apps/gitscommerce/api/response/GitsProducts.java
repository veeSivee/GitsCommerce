package vee.apps.gitscommerce.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by binaryvi on 12/05/2016.
 */
public class GitsProducts {
    @SerializedName("id")
    private String id;

    @SerializedName("rating")
    private String rating;

    @SerializedName("nama")
    private String nama;

    @SerializedName("jenis")
    private String jenisProduct;

    @SerializedName("harga")
    private String harga;

    @SerializedName("diskon")
    private String diskon;

    @SerializedName("stok")
    private String stok;

    @SerializedName("url_foto")
    private String urlFoto;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenisProduct() {
        return jenisProduct;
    }

    public void setJenisProduct(String jenisProduct) {
        this.jenisProduct = jenisProduct;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
