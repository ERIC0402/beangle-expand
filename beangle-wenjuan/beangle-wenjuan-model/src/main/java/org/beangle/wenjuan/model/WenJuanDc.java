package org.beangle.wenjuan.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="org.beangle.wenjuan.model.WenJuanDc")
@DiscriminatorValue(value = "DCWJ")
public class WenJuanDc extends WenJuan {

}
