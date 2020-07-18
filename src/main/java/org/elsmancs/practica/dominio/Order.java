package org.elsmancs.practica.dominio;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_orders")
public class Order {

        @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
        @Column(name="ord_id")
        private Long id;

        @OneToOne
        @JoinColumn(name="ord_wizard")
        private Wizard name;

        @ManyToOne
        @JoinColumn(name="ord_item")
        private MagicalItem item;

        public Long getId() {
            return this.id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Wizard getWizard() {
            return this.name;
        }

        public void setWizard(Wizard name) {
            this.name = name;
        }

        public MagicalItem getItem() {
            return this.item;
        }

        public void setItem(MagicalItem item) {
            this.item = item;
        }
}
