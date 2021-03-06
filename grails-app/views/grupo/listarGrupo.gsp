<!DOCTYPE html>
<html lang="pt-br">
<head>
<title>Grupo . Módulo Login</title>
<meta name="layout" content="public" />
<g:javascript src="jquery.js" />
<g:javascript src="jquery.maskedinput.js" />
</head>
</head>
<body>
	<script>
       function deletar(id) {
        var resposta = confirm("Deseja exluir este Grupo?");

        if (resposta == true){
        location.href="/projetoMetafora/grupo/deletar/"+id }

       }
 </script>
  	<script>
function printDiv(id)
{
  var divToPrint=document.getElementById(id);
  newWin= window.open("");
  newWin.document.write("PREFEITURA DE SÃO GONÇALO DO AMARANTE <br>");
  newWin.document.write("RELATÓRIO GERENCIAL <br><br>");
  newWin.document.write(" ");
  newWin.document.write(divToPrint.outerHTML);
  newWin.print();
  newWin.close();
}
</script>
 
	<section class="content-header">
		<h1>
			Grupos <small>Visualização e Gerenciamento</small>
		</h1>
		<ol class="breadcrumb">
			<li class="active"><g:link controller="Layout" action="index">
					<i class="fa fa-dashboard"></i> Inicio</g:link></li>
			<li><g:link controller="Grupo" action="listar">Grupos</g:link></li>
		</ol>
	</section>
	<!-- CORPO DA PÁGINA -->
	<section class="content">
		<div>
			<g:if test="${ok}">
				<div class="alert alert-success">
					${ok}
				</div>
			</g:if>
			<g:if test="${erro}">
				<div class="alert alert-danger">
					${erro}
				</div>
			</g:if>
			<table id="example" class="table table-striped table-hover">
				<g:if test="${!grupos?.isEmpty()})"></g:if>
				<thead>
					<tr>
						<th style="width: 55px;"></th>
						<th style="width: 280px;">Nome</th>
						<th style="width: 60px;">Descrição</th>
					</tr>
				</thead>
				<tbody>
					<g:each in='${grupos?}'>

						<tr class='linha_registro'>
							<td>
								<div style="margin-left: -35px" class="opcoes">
									<ul style="display: inline">
										<g:if test="${perm2}">
										
										<li class="btn btn-primary btn-xs btn-flat"><a
											style="color: #fff"
											href="/projetoMetafora/grupo/editarGrupo/${it.id}"><span
												class="glyphicon glyphicon-pencil"></span></a></li>
										<li onclick="deletar(${it.id})"
											class="btn btn-danger btn-xs btn-flat"><span
											class="glyphicon glyphicon-remove"></span></li>
											
											</g:if>
									</ul>

								</div>
							</td>
							<td>
								${it.nome}
							</td>
							<td>
								${it.descricao}
							</td>
						</tr>
					</g:each>

				</tbody>
			</table>
			<script type="text/javascript">
			$(document).ready(function() {
				$('#example').DataTable();
				var tabela = $('#example').dataTable();
				// Ordena por nome e "desempata" com o id
				tabela.fnSort([ [ 1, 'asc' ] ]);
			});
		</script>
			<!-- Button trigger modal -->
				<g:if test="${perm2}">
			<button class="btn btn-primary btn-flat" data-toggle="modal"
				data-target="#myModal">
				<i class="fa fa-plus"></i> Novo Grupo
			</button>
			</g:if>
			<button class="btn btn-danger btn-flat" onClick="printDiv('example')">
				<i class="glyphicon glyphicon-print"></i> Imprimir
			</button>
			<!-- Modal -->
				<g:if test="${perm2}">
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Cadastro de
								Grupo</h4>
						</div>
						<div class="modal-body">
							<g:form controller="Grupo" action="salvar" class="form">
								<fieldset>
									<div class="form-heading">
										<label>Nome</label>
										<div class="controls">
											<g:textField class="form-control" required="true" name="nome" value="" />
										</div>
									</div>
									<br>
									<div class="form-heading">
										<label>Descrição</label>
										<div class="controls">
											<g:textField class="form-control" required="true" name="descricao" value="" />
										</div>
									</div>
									<br>											
									<section class="sidebar">	
									<label>Permissões</label>						
										<ul class="sidebar-menu">
											<g:each in='${schemas?}' var="schema">
											<li class="treeview">
												<a href="#">
													<i class="fa fa-users"></i><span>${schema.schemaname}</span> 
													<i class="fa fa-angle-left pull-right"></i>
												</a>
												<ul class="treeview-menu">
													<g:each in='${tabelas?}' var="table">	
													<g:if test="${table.schemaname == schema.schemaname}">
													<li><g:link controller="Aluno" action="listar">
														<i class="fa fa-graduation-cap"></i> ${table.tabela}</g:link>
														
														<div style="margin-top: -6.5%; margin-left: 50%; padding: 2%;" class="radio">
														  	<label class="radio-inline">
														  		<input type="radio" name="comp${table.schemaname}-${table.tabela}" value="${table.schemaname}-${table.tabela}-1"> L
															</label>
															<label class="radio-inline">
														  		<input type="radio" name="comp${table.schemaname}-${table.tabela}" value="${table.schemaname}-${table.tabela}-2"> E
															</label>
															<label class="radio-inline">
														  		<input type="radio" name="comp${table.schemaname}-${table.tabela}" value="${table.schemaname}-${table.tabela}-0" checked> N
															</label>
														</div>
													</li>
													</g:if>
													</g:each>			
												</ul>
											</li>
											</g:each>
										</ul>				
									</section>
								</fieldset>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary btn-flat">
										<i class="fa fa-save"></i> Cadastrar
									</button>
									<input type="reset" class="btn btn btn-flat" value="Limpar">
								</div>
							</g:form>
						</div>
					</div>
				</div>
			</div>
		</g:if>
		</div>
	</section>
</body>
<script type="text/javascript">
	$(document).ready(function () {
		$('label.tree-toggler').click(function () {
			$(this).parent().children('ul.tree').toggle(300);
		});
	});
</script>
</html>
