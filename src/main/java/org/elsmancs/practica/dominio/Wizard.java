package org.elsmancs.practica.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_wizards")
public class Wizard {

    @Id
    @Column(name="wizard_name")
    private String name;

    @Column(name="wizard_dexterity")
    private int dexterity;

    @Enumerated(EnumType.STRING)
    @Column(name="wizard_person")
    private WizardType person;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public WizardType getPerson() {
        return this.person;
    }
}