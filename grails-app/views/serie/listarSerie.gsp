<!DOCTYPE html>
<html lang="pt-br">
<head>
<title>Série . Módulo Acadêmico</title>
<meta name="layout" content="public" />
</head>
<body>

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

	<script>
       function deletar(id) {
        var resposta = confirm("Deseja exluir esta Série?");

        if (resposta == true){
        location.href="/projetoMetafora/serie/deletar/"+id }

       }
 </script>
	<section class="content-header">
		<h1>
			Séries <small>Visualização e Gerenciamento</small>
		</h1>
		<ol class="breadcrumb">
			<li class="active"><g:link controller="Layout" action="index">
					<i class="fa fa-dashboard"></i> Inicio</g:link></li>
			<li><g:link controller="Serie" action="listar">Séries</g:link></li>
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
			<table id="" class="table table-striped table-hover example">
				<thead>
					<tr>
						<th style="width: 60px;"></th>
						<th>Série</th>
						<th>Vagas</th>
						<th>Data de Inicio</th>
						<th>Data do Termino</th>

					</tr>
				</thead>
				<tbody>
					<g:each in='${serie?}'>
						<tr class='linha_registro'>
							<td>
								<div style="margin-left: -35px" class="opcoes">
									<ul style="display: inline">
										<g:if test="${perm2}">
										<li class="btn btn-primary btn-xs btn-flat"><a
											style="color: #fff"
											href="/projetoMetafora/serie/editarSerie/${it.id}"><span
												class="glyphicon glyphicon-pencil"></span></a></li>
										<li onclick="deletar(${it.id})"
											class="btn btn-danger btn-xs btn-flat"><span
											class="glyphicon glyphicon-remove"></span></li>
											
										</g:if>
									</ul>

								</div>
							</td>

							<td>
								${it.serie}
							</td>
							<td>
								${it.numeroDeVagas}
							</td>
							<td><g:formatDate format="dd-MM-yyyy"
									date="${it.dataDeInicio}" /></td>
							<td><g:formatDate format="dd-MM-yyyy"
									date="${it.dataDeTermino}" /></td>
						</tr>
					</g:each>

				</tbody>
			</table>
			
			<!-- Button trigger modal -->
			<g:if test="${perm2}">
			<button class="btn btn-primary btn-flat" data-toggle="modal"
				data-target="#myModal">
				<i class="fa fa-plus"></i> Nova Série
			</button>
			</g:if>
			<button class="btn btn-danger btn-flat" onClick="printDiv('example')">
				<i class="glyphicon glyphicon-print"></i> Imprimir
			</button>

			<!-- Modal -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Cadastro de Série</h4>
						</div>
						<div class="modal-body">
							<g:form controller="Serie" action="salvar" class="form">
								<fieldset>
									<div class="form-heading">
										<label>Série</label>
										<div class="controls">
											<g:textField class="form-control" name="serie" value="" required="true"/>
										</div>
									</div>
									<br>
									<div class="form-heading">
										<label>Número de Vagas</label>
										<div class="controls">
											<g:textField class="form-control" name="numeroDeVagas"
												value="" required="true"/>
										</div>
									</div>
									<br>
									<div class="form-heading">
										<label>Data de Inicio</label>
										<div class="controls">
											<g:formatDate format="yyyy-MM-dd" date="${date}" />
											<g:datePicker noSelection="['':'']" precision="day"
												class="form-control" required="true" name="dataDeInicio"
												value="" />
										</div>
									</div>
									<br>
									<div class="form-heading">
										<label>Data do Terminio</label>
										<div class="controls">
											<g:formatDate format="yyyy-MM-dd" date="${date}" />
											<g:datePicker noSelection="['':'']" precision="day"
												class="form-control" required="true" name="dataDeTermino"
												value="" />
										</div>
									</div>
									<br>
								</fieldset>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary btn-flat"><i class="fa fa-save"></i> Cadastrar</button>
									<input type="reset" class="btn btn btn-flat" value="Limpar">
								</div>
							</g:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>