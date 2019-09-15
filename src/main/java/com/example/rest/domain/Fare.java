package com.example.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlRootElement(name = "Fare")
@XmlAccessorType(XmlAccessType.FIELD)
public class Fare {
    @XmlAttribute(name = "class")
    private String fareClass;
    @XmlElement(name = "BasePrice")
    private String basePrice;
    @XmlElement(name = "Fees")
    private String fees;
    @XmlElement(name = "Tax")
    private String tax;
}
