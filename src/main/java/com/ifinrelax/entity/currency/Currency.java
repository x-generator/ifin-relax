package com.ifinrelax.entity.currency;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Timur Berezhnoi
 */
@Entity
public class Currency {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String sign;

    @Size(min = 3, max = 3, message = "user.currency.code.length")
    private String code;

    public Currency(String sign, String code) {
        this.sign = sign;
        this.code = code;
    }

    public Currency() {}

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "sign='" + sign + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}