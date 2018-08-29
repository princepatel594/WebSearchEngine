<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	
</script>
<div class="limiter">
	<!--	<div class="container-table100"> -->
	<div class="wrap-table100">
		<div class="table100 ver3 m-b-110">
			<div class="table100-head">
				<table>
					<thead>
						<tr class="row100 head">
							<th class="cell100 column1">Website</th>
							<th class="cell100 column2">Frequency</th>
						</tr>
					</thead>
				</table>
			</div>

			<div class="table100-body js-pscroll">
				<table>
					<tbody>
						<c:choose>
							<c:when test="${not empty frequencyList}">
								<c:forEach items="${frequencyList}" var="entry">
									<c:choose>
										<c:when test="${entry.value != 0}">
											<tr class="row100 body">
												<td class="cell100 column1">${entry.key}</td>
												<td class="cell100 column2">${entry.value}</td>
											</tr>
										</c:when>
									</c:choose>
								</c:forEach>
							</c:when>
							<c:when test="${empty frequencyList}">
								<tr class="row100 body">
									<td class="cell100 column1" colspan="2">No Rows Found</td>
								</tr>
							</c:when>
						</c:choose>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>
<!--</div>-->

