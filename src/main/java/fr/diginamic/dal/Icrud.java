package fr.diginamic.dal;

import jakarta.transaction.Transactional;

public interface Icrud {
    @Transactional
    void ajouter();

    @Transactional
    void modifier();

    @Transactional
    void supprimer();
}
