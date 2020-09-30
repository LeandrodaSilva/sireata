import br.edu.utfpr.dv.sireata.bo.AtaBO;
import br.edu.utfpr.dv.sireata.dao.*;
import br.edu.utfpr.dv.sireata.model.*;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

public class AtaBOTests {
    private final Date date = new Date();

    private final Ata ata = new Ata();
    private final Pauta pauta = new Pauta();
    private final Orgao orgao = new Orgao();
    private final Departamento departamento = new Departamento();
    private final Campus campus = new Campus();
    private final Usuario usuario = new Usuario();

    private final AtaBO ataBO = new AtaBO();

    private final AtaDAO ataDAO = new AtaDAO();
    private final PautaDAO pautaDAO = new PautaDAO();
    private final CampusDAO campusDAO = new CampusDAO();
    private final DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final OrgaoDAO orgaoDAO = new OrgaoDAO();

    @Before
    public void setUp() throws Exception {
        this.date.setDate(Calendar.DATE);
        this.date.setMonth(Calendar.SEPTEMBER);
        this.date.setYear(Calendar.YEAR);
        this.date.setHours(Calendar.HOUR);
        this.date.setMinutes(Calendar.MINUTE);

        this.usuario.setIdUsuario(2);
        this.usuario.setNome("Leandro");
        this.usuario.setEmail("leandrosilva.2017@alunos.utfpr.edu.br");
        this.usuario.setLogin("leandro");
        this.usuario.setExterno(false);
        this.usuario.setSenha("12345678");

        this.campus.setIdCampus(1);
        this.campus.setNome("Cornélio Procópio");
        this.campus.setEndereco("Endereço");
        this.campus.setAtivo(true);
        this.campus.setSite("www.apoio.cp.utfpr.edu.br");

        this.departamento.setIdDepartamento(2);
        this.departamento.setCampus(this.campus);
        this.departamento.setNome("DIPROSI");
        this.departamento.setAtivo(true);
        this.departamento.setSite("www.diprosi.cp.utfpr.edu.br");
        this.departamento.setNomeCompleto("DIPROSI");

        this.orgao.setIdOrgao(1);
        this.orgao.setDepartamento(this.departamento);
        this.orgao.setNome("Nome do órgão");
        this.orgao.setNomeCompleto("nome completo");
        this.orgao.setDesignacaoPresidente("designacao presidente");
        this.orgao.setPresidente(this.usuario);
        this.orgao.setSecretario(this.usuario);
        this.orgao.setAtivo(true);

        this.ata.setIdAta(1);
        this.ata.setOrgao(this.orgao);
        this.ata.setPresidente(this.usuario);
        this.ata.setSecretario(this.usuario);
        this.ata.setTipo(Ata.TipoAta.ORDINARIA);
        this.ata.setData(this.date);
        this.ata.setNumero(1);
        this.ata.setDataLimiteComentarios(this.date);
        this.ata.setLocal("local");
        this.ata.setLocalCompleto("local completo");
        this.ata.setConsideracoesIniciais("consideracoes iniciais");

        this.pauta.setIdPauta(1);
        this.pauta.setAta(this.ata);
        this.pauta.setOrdem(1);
        this.pauta.setTitulo("titulo da pauta");
        this.pauta.setDescricao("descricao da pauta");
    }

    @Test
    public void getDataExtenso() {
        Date date = new Date();
        date.setMonth(Calendar.SEPTEMBER);
        date.setYear(Calendar.YEAR);
        date.setHours(Calendar.HOUR);
        date.setMinutes(Calendar.MINUTE);

        assertEquals(Calendar.SEPTEMBER, date.getMonth());
    }

    @Test
    public void usuarioDAOSalvar1Test() throws SQLException {
        this.usuarioDAO.salvar(this.usuario);

        Usuario usuario = this.usuarioDAO.buscarPorId(2);

        assertEquals("Leandro", usuario.getNome());
        assertEquals("leandro", usuario.getLogin());
    }

    @Test
    public void usuarioDAOSalvar2Test() throws SQLException {
        this.usuario.setLogin("user");
        this.usuarioDAO.salvar(this.usuario);

        Usuario usuario = this.usuarioDAO.buscarPorId(2);

        assertEquals("user", usuario.getLogin());
        assertEquals(2, usuario.getIdUsuario());
    }

    @Test
    public void campusDAOSalvar1Test() throws SQLException {
        this.campusDAO.salvar(this.campus);
        Campus campus = this.campusDAO.buscarPorId(1);

        assertEquals("Cornélio Procópio", campus.getNome());
        assertEquals(1, campus.getIdCampus());
    }

    @Test
    public void departamentoDAOSalvar1Test() throws SQLException {
        Departamento departamento = this.departamentoDAO.buscarPorId(2);

        assertEquals(2, departamento.getIdDepartamento());
        assertEquals("DIPROSI", departamento.getNome());
        assertEquals("Cornélio Procópio", departamento.getCampus().getNome());
    }

    @Test
    public void orgaoDAOSalvar1Test() throws SQLException {
        this.orgaoDAO.salvar(this.orgao);

        Orgao orgao = this.orgaoDAO.buscarPorId(1);

        assertEquals("Nome do órgão", orgao.getNome());
        assertEquals(2, orgao.getPresidente().getIdUsuario());
        assertEquals("Leandro", orgao.getPresidente().getNome());
        assertEquals(1, orgao.getIdOrgao());
        assertEquals(2, orgao.getDepartamento().getIdDepartamento());
        assertEquals("DIPROSI", orgao.getDepartamento().getNome());
    }

    @Test
    public void ataDAOSalvar1Test() throws SQLException, CloneNotSupportedException {
        this.ata.setLocal("local2");
        this.ataDAO.salvar(this.ata);
        Ata ata = this.ataDAO.buscarPorId(1);
        ata.setLocal("local");
        Ata ataClone = ata.clone();
        this.ataDAO.salvar(ataClone);

        assertEquals("local completo", ata.getLocalCompleto());
        assertEquals(1, ata.getIdAta());
        assertSame(ataClone.getLocal(), ata.getLocal());
    }

    @Test
    public void pautaDAOSalvar1Test() throws SQLException {
        this.pautaDAO.salvar(this.pauta);
        Pauta pauta = this.pautaDAO.buscarPorId(1);

        assertEquals("titulo da pauta", pauta.getTitulo());
        assertEquals("descricao da pauta", pauta.getDescricao());
        assertEquals(1, pauta.getIdPauta());
    }

    @Test
    public void ataBOGerarAtaReport1Test() throws Exception {
        assertEquals(
                "Aos cinco dias do mês de setembro de um mil e novecentos e " +
                "um, às dez horas e doze minutos, no local completo realizou-se a primeira " +
                "reunião ordinária de 1901 do(a) nome completo, a qual foi conduzida pelo(a) " +
                "designacao presidente Leandro e teve como pauta: <b>(1) titulo da pauta.</b> " +
                "consideracoes iniciais <b>(1)  titulo da pauta</b>, descricao da pauta  " +
                "Nada mais havendo a tratar, deu-se por encerrada a reunião, da " +
                "qual eu, Leandro, lavrei a presente ata que, após aprovada, vai assinada por " +
                "mim e pelos demais presentes.",
                ataBO.gerarAtaReport(1).getTexto()
        );
    }
}
