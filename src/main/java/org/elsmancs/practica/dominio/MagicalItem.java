package org.elsmancs.practica.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_items")
public class MagicalItem {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="item_id")
    private Long id;

    @Column(name="item_name")
    private String name;

    @Column(name="item_quality")
    private Integer quality;

    @Column(name="item_type")
    private String type;
    
    public MagicalItem() {
    	
    }
    
    public MagicalItem(String name, int quality, String type) {
    	this.name = name;
    	this.quality = quality;
    	this.type = type;
    }
    

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuality() {
        return this.quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
