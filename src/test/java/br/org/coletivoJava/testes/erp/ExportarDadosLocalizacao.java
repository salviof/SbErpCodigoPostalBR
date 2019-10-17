/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.testes.erp;

import com.super_bits.modulosSB.Persistencia.ConfigGeral.ConfigPersistenciaPadrao;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.Persistencia.geradorDeId.GeradorIdBairro;
import com.super_bits.modulosSB.Persistencia.geradorDeId.GeradorIdCidade;
import com.super_bits.modulosSB.Persistencia.geradorDeId.GeradorIdNomeUnico;
import com.super_bits.modulosSB.Persistencia.registro.persistidos.modulos.CEP.Bairro;
import com.super_bits.modulosSB.Persistencia.registro.persistidos.modulos.CEP.Cidade;
import com.super_bits.modulosSB.Persistencia.registro.persistidos.modulos.CEP.FabUnidadesFederativas;
import com.super_bits.modulosSB.Persistencia.registro.persistidos.modulos.CEP.UnidadeFederativa;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UTilSBCoreInputs;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.HashedMap;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.junit.Test;

/**
 *
 * @author desenvolvedorninja01
 * @since 17/10/2019
 * @version 1.0
 */
public class ExportarDadosLocalizacao {

    private final Map<Integer, Cidade> mapaCidade = new HashedMap();
    private final Map<Integer, UnidadeFederativa> mapUF = new HashedMap();
    private final Map<Integer, Bairro> mapabairro = new HashedMap();
    private final GeradorIdNomeUnico geradorid = new GeradorIdNomeUnico();

    public void addItemCep(ItfBeanSimples item) {

        if (item instanceof Cidade) {
            GeradorIdCidade gerador = new GeradorIdCidade();
            gerador.generate(null, item);
            mapaCidade.put(((Cidade) item).getId(), (Cidade) item);
        }
        if (item instanceof UnidadeFederativa) {
            GeradorIdNomeUnico gerador = new GeradorIdNomeUnico();
            gerador.generate(null, item);
            mapUF.put(((UnidadeFederativa) item).getId(), (UnidadeFederativa) item);
        }
        if (item instanceof Bairro) {
            GeradorIdBairro gerador = new GeradorIdBairro();
            gerador.generate(null, item);
            mapabairro.put(((Bairro) item).getId(), (Bairro) item);
        }
    }

    public void registrarItens(String[] pColunas) {
        try {
            String coluna2 = pColunas[1];
            if (coluna2.contains("/")) {

                Cidade cidade = new Cidade();
                String[] cidadeEstado = coluna2.split("/");
                String cidadestr = cidadeEstado[0];
                String estadoStr = cidadeEstado[1].substring(0, 2);

                UnidadeFederativa uf = new UnidadeFederativa();
                uf.setUF(estadoStr);

                UnidadeFederativa ufobj = FabUnidadesFederativas.getUF(estadoStr);
                cidade.setUnidadeFederativa(ufobj);
                cidade.setNome(cidadestr);

                addItemCep(uf);
                addItemCep(cidade);
                if (pColunas.length >= 3) {
                    Bairro bairro = new Bairro();
                    String coluna3 = pColunas[2];
                    bairro.setNome(coluna3);
                    bairro.setCidade(cidade);
                }
            } else {
                Cidade cidade = new Cidade();
                String coluna3 = pColunas[2];
                String coluna4 = pColunas[3];

                String estadoStr = coluna2;
                String cidadeStr = coluna3;
                UnidadeFederativa uf = new UnidadeFederativa();
                uf.setUF(estadoStr);

                addItemCep(uf);
                UnidadeFederativa ufobj = FabUnidadesFederativas.getUF(estadoStr);
                cidade.setUnidadeFederativa(ufobj);
                cidade.setNome(cidadeStr);
                Bairro bairro = new Bairro();

                bairro.setNome(coluna4);
                bairro.setCidade(cidade);
                addItemCep(uf);
                addItemCep(cidade);
                addItemCep(bairro);
            }

        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro", t);
        }
    }

    @Test
    public void lerDados() {
        SBCore.configurar(new ConfigCoreApiErpCodigoPostal(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        String teste = "São Paulo/SP";
        String[] cidadeEstado = teste.split("/");

        SBPersistencia.configuraJPA(new ConfigPersistenciaPadrao() {
            @Override
            public String bancoPrincipal() {
                return "bancoCepInicial";
            }

            @Override
            public Class<? extends ItfFabrica>[] fabricasRegistrosIniciais() {
                return new Class[0];
            }
        });
        String caminhoPastaDados = SBCore.getCaminhoDesenvolvimento() + "/dados/arquivoImportacao";
        File pastaDados = new File(caminhoPastaDados);

        for (File pasta : pastaDados.listFiles()) {
            if (pasta.isFile()) {
                String arquivo = pasta.getAbsolutePath();

                List<String> registros = UTilSBCoreInputs.getStringsByArquivoLocal(arquivo);
                registros.parallelStream().map((reg) -> reg.split("\t")).forEachOrdered((colunas) -> {
                    registrarItens(colunas);
                });
                System.out.println(mapaCidade.size());
                System.out.println(mapUF.size());
                System.out.println(mapabairro.size());
            }
        }

        mapUF.values().forEach(uf -> {
            UtilSBPersistencia.mergeRegistro(uf);
        });

        mapaCidade.values().forEach(uf -> {
            UtilSBPersistencia.mergeRegistro(uf);
        });
        mapabairro.values().forEach(uf -> {
            UtilSBPersistencia.mergeRegistro(uf);
        });

    }

}
