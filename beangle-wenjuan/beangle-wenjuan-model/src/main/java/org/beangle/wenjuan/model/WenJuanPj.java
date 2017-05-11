package org.beangle.wenjuan.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="org.beangle.wenjuan.model.WenJuanPj")
@DiscriminatorValue(value = "PJWJ")
public class WenJuanPj extends WenJuan {

}
