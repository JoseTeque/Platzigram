package com.acer.cursos.platzigram.modelo;

public class Picture {
    private String imagen;
    private String fechacard;
    private String usernamecard;
    private String likecard;

    public Picture(String imagen, String fechacard, String usernamecard, String likecard) {
        this.imagen = imagen;
        this.fechacard = fechacard;
        this.usernamecard = usernamecard;
        this.likecard = likecard;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFechacard() {
        return fechacard;
    }

    public void setFechacard(String fechacard) {
        this.fechacard = fechacard;
    }

    public String getUsernamecard() {
        return usernamecard;
    }

    public void setUsernamecard(String usernamecard) {
        this.usernamecard = usernamecard;
    }

    public String getLikecard() {
        return likecard;
    }

    public void setLikecard(String likecard) {
        this.likecard = likecard;
    }
}
