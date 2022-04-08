package br.org.coletivoJava.fw.api.erp.codigopostalbr;

import javax.inject.Qualifier;
import br.org.coletivoJava.fw.api.erp.codigoPostal.br.ItfCodigoPostalBR;
import com.super_bits.modulosSB.SBCore.modulos.erp.InfoReferenciaApiErp;
import br.org.coletivoJava.fw.api.erp.codigoPostal.br.ERPCodigoPostalBR;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Qualifier
@InfoReferenciaApiErp(tipoObjeto = ERPCodigoPostalBR.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CodigoPostalBRServicoColetivoJavaTestes {
}