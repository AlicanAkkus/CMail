<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output indent="yes" encoding="utf-8" method="html"
		standalone="yes" />

	<xsl:template match="/">

		<html>
			<body>
				<h2>Tickets</h2>
				<table border="1">
					<tr bgcolor="#9acd32">
						<th>name</th>
						<th>surname</th>
						<th>gender</th>
						<th>age</th>
						<th>job</th>
						<th>from</th>
						<th>to</th>
					</tr>
					<xsl:for-each select="tickets/ticket">
						<tr>
							<td>
								<xsl:value-of select="name" />
							</td>
							<td>
								<xsl:value-of select="surname" />
							</td>
							<td>
								<xsl:value-of select="gender" />
							</td>
							<td>
								<xsl:value-of select="age" />
							</td>
							<td>
								<xsl:value-of select="job" />
							</td>
							<td>
								<xsl:value-of select="from" />
							</td>
							<td>
								<xsl:value-of select="to" />
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
		<xsl:for-each select="//tickets/ticket" />

	</xsl:template>
</xsl:stylesheet>