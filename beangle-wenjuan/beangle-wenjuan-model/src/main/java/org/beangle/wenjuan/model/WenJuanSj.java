package org.beangle.wenjuan.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="org.beangle.wenjuan.model.WenJuanSj")
@DiscriminatorValue(value = "SJWJ")
public class WenJuanSj extends WenJuan {

}
