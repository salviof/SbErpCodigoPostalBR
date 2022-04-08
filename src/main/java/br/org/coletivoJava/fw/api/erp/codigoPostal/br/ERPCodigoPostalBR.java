/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.api.erp.codigoPostal.br;

/**
 *
 * @author desenvolvedorninja01
 */
import com.super_bits.modulosSB.SBCore.modulos.erp.ApiERPColetivoJavaFW;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfApiErpSuperBits;

@ApiERPColetivoJavaFW(descricaoApi = "Codigo Postal BR", nomeApi = "CodigoPostalBR", slugInicial = "CodigoPostalBR")
public enum ERPCodigoPostalBR implements ItfApiErpSuperBits<ItfCodigoPostalBR> {

    REPUBLICA_VIRUTAL,
    BANCO_LOCAL,
    SERVICO_COLETIVO_JAVA,
    API_FREE_REDUNTANTE;

    @Override
    public Class<? extends ItfCodigoPostalBR> getInterface() {
        return ItfCodigoPostalBR.class;
    }

}
