/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.api.erp.codigoPostal.br;

import br.org.coletivoJava.fw.api.erp.codigopostalbr.InfoRespostaCepWebService;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.cep.ComoLocal;
import java.util.List;

/**
 * @author desenvolvedorninja01
 * @since 16/10/2019
 * @version 1.0
 */
public interface ItfCodigoPostalBR {

    public boolean configuraEndereco(String cep, ComoLocal pLocal);

    public List<String> cepsPorEndereco(String pEndereco);

    public boolean isCepExiste(String pCep);

    public boolean configurarPosicaoGeogafica(ComoLocal pLocal);

    public boolean contribuirCadastroNovoEndereco(ComoLocal pLocal);

    public InfoRespostaCepWebService getInfoRespostaWebService(String cep);

}
