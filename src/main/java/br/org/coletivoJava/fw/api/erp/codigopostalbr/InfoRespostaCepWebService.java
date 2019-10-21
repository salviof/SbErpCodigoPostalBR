/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.api.erp.codigopostalbr;

import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.cep.ItfLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coletivojava.fw.api.tratamentoErros.ErroPreparandoObjeto;

/**
 *
 * @author desenvolvedorninja01
 * @since 21/10/2019
 * @version 1.0
 */
public class InfoRespostaCepWebService {

    private final String unidadeFederativaStr;
    private final String cidadeStr;
    private final String bairroStr;
    private final String logadouroStr;

    public InfoRespostaCepWebService(String pUnidadeFederativa, String pCidade, String pBairro, String pLogadouro) {
        unidadeFederativaStr = pUnidadeFederativa;
        cidadeStr = pCidade;
        bairroStr = pBairro;
        logadouroStr = pLogadouro;
    }

    public boolean applicarDados(ItfLocal pLocal) {
        try {
            pLocal.prepararNovoObjeto();
        } catch (ErroPreparandoObjeto ex) {
            Logger.getLogger(InfoRespostaCepWebService.class.getName()).log(Level.SEVERE, null, ex);
        }

        pLocal.setNome(logadouroStr);
        try {
            pLocal.getComoLocalPostavel().setLogradouro(logadouroStr);
        } catch (Throwable t) {
            Logger.getLogger(InfoRespostaCepWebService.class.getName()).log(Level.SEVERE, null, t);
        }
        pLocal.getBairro().setNome(bairroStr);

        pLocal.getBairro().getCidade().setNome(cidadeStr);
        pLocal.getBairro().getCidade().getUnidadeFederativa().setNome(unidadeFederativaStr);
        pLocal.getBairro().getCidade().getUnidadeFederativa().setSigla(unidadeFederativaStr);
        return true;
    }

}
