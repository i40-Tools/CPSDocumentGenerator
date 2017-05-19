<?xml version="1.0" encoding="utf-8"?>

<!DOCTYPE stylesheet  [
<!ENTITY rdf "http://www.w3.org/1999/02/22-rdf-syntax-ns#">
<!ENTITY rdfs "http://www.w3.org/2000/01/rdf-schema#">
<!ENTITY dc "http://purl.org/dc/elements/1.1/">
<!ENTITY opcua "https://w3id.org/i40/opcua/">
<!ENTITY xsd "http://www.w3.org/2001/XMLSchema#">
<!ENTITY schema "http://schema.org/">
]>


<xsl:transform version="2.0" 
               xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
               xmlns:xd="http://www.pnp-software.com/XSLTdoc"
               xmlns:krextor="http://kwarc.info/projects/krextor"
               xmlns:krextor-genuri="http://kwarc.info/projects/krextor/genuri"
               xmlns:xs="http://www.w3.org/2001/XMLSchema"
               xmlns:ss="http://opcfoundation.org/UA/2008/02/Types.xsd"
               xmlns:se="http://opcfoundation.org/UA/2011/03/UANodeSet.xsd"
                      exclude-result-prefixes="">                      
<!--  UANodeSet -->
<!--<xsl:param name="autogenerate-fragment-uris" select="'generate-id'"/>-->
 <xsl:template match="/" mode="krextor:main">
      <xsl:apply-imports>
        <xsl:with-param
          name="krextor:base-uri"
          select="xs:anyURI('https://w3id.org/i40/opcua/')"
          as="xs:anyURI"
          tunnel="yes"/>
      </xsl:apply-imports>
 </xsl:template>

 <xd:doc>uses ElementN as the fragment URI of the N-th occurrence of an element named
   <code>Element</code> in document order, starting from N=1.</xd:doc>
    <xsl:function name="krextor:global-element-index" as="xs:string?">
	<xsl:param name="node" as="node()"/>
	<xsl:value-of select="concat(local-name($node), count(root($node)//*[local-name() eq local-name($node) and . &lt;&lt; $node]) + 1)"/>
    </xsl:function>

    <!-- copied and adapted from generic/uri.xsl -->
    <xsl:template match="krextor-genuri:global-element-index" as="xs:string?">
	<xsl:param name="node" as="node()"/>
	<xsl:param name="base-uri" as="xs:anyURI"/>
	<xsl:sequence select="
	    resolve-uri(krextor:global-element-index($node), $base-uri)"/>
    </xsl:template>
    
<xsl:param name="autogenerate-fragment-uris" select="'global-element-index'" />
<!-- <xsl:param name="autogenerate-fragment-uris" select="'pseudo-xpath'" /> -->
<xsl:variable name="krextor:resources">
	<se:UANodeSet type="&opcua;UANodeSet"/>

	<se:NamespaceUris type="&opcua;NamespaceUris" related-via-properties="&opcua;hasNamespaceUris"/>
	
	<se:Aliases type="&opcua;Aliases" related-via-properties="&opcua;hasAliases"/>
	
	<se:UAObject type="&opcua;UAObject" related-via-properties="&opcua;hasUAObject"/>
	
	<se:UAVariable type="&opcua;UAVariable" related-via-properties="&opcua;hasUAVariable"/>
	
	<se:UAObjectType type="&opcua;UAObjectType" related-via-properties="&opcua;hasUAObjectType"/>

	<se:Alias type="&opcua;Alias" related-via-properties="&opcua;hasAlias"/>

	<se:Uri type="&opcua;Uri" related-via-properties="&opcua;hasUri"/>

	<se:Descriprion type="&opcua;Description" related-via-properties="&opcua;hasDescription"/>

	<se:References type="&opcua;References" related-via-properties="&opcua;hasReferences"/>

	<se:Reference type="&opcua;Reference" related-via-properties="&opcua;hasReference"/>

	<se:Value type="&opcua;Value" related-via-properties="&opcua;hasValue"/>

    <se:RefSemantic type="&opcua;RefSemantic" related-via-properties="&opcua;hasRefSemantic"/>
     <se:NodeId type="&opcua;NodeId" related-via-properties="&opcua;hasNodeId"/>

</xsl:variable>

<xsl:template match="se:UANodeSet
                    |se:UANodeSet/se:NamespaceUris
					|se:UANodeSet/se:Aliases
					|se:UANodeSet/se:UAObject
					|se:UANodeSet/se:UAObjectType
					|se:UANodeSet/se:UAVariable
					|se:Value
					|se:References
					|se:RefSemantic
					|se:Reference" mode="krextor:main">
	   <xsl:apply-templates select="." mode="krextor:create-resource"/>
</xsl:template>

<xsl:variable name="krextor:literal-properties">

 
<!-- Aliases -->
		<se:Alias property="&opcua;hasAlias" krextor:attribute="yes" datatype="&xsd;string"/>

<!-- UAObject  -->
		<se:NodeId property="&opcua;hasNodeId" krextor:attribute="yes" datatype="&xsd;string"/>
	    <se:BrowseName property="&opcua;hasBrowseName" krextor:attribute="yes" datatype="&xsd;string"/>

<!-- Reference -->
		<se:ReferenceType property="&dc;referenceType" krextor:attribute="yes" datatype="&xsd;string"/>
		<se:IsForward property="&dc;isForward" krextor:attribute="yes" datatype="&xsd;boolean"/>
		<se:Reference property="&opcua;hasValue"/>

<!-- UAVariable -->
		<se:ParentNodeId property="&dc;parentNodeId" krextor:attribute="yes" datatype="&xsd;string"/>
		<se:DataType property="&dc;hasDataType" krextor:attribute="yes" datatype="&xsd;string"/>
		<se:AccessLevel property="&dc;accessLevel" krextor:attribute="yes" datatype="&xsd;string"/>
		<se:UserAccessLevel property="&dc;userAccessLevel" krextor:attribute="yes" datatype="&xsd;string"/>
		<se:DisplayName property="&opcua;hasDisplayName"/>
	   
<!-- Value  -->
		<ss:String property="&opcua;hasString" krextor:attribute="yes" datatype="&xsd;string"/>

	

<!-- the following mapping rules will be simplified in the second example, this version can be treated as standard test case -->
</xsl:variable>
<xsl:template match="se:UANodeSet/se:Aliases/se:Alias/@Alias
	                  |se:UANodeSet/se:UAObject/@NodeId
	                  |se:UANodeSet/se:UAObject/@BrowseName
	                  |se:UANodeSet/se:UAObject/se:References/se:Reference/@ReferenceType
	                  |se:UANodeSet/se:UAObject/se:References/se:Reference/@IsForward
	                  |se:UANodeSet/se:UAObject/se:References/se:Reference	                 
	                  |se:UANodeSet/se:UAVariable/@NodeId
	                  |se:UANodeSet/se:UAVariable/@BrowseName
	                  |se:UANodeSet/se:UAVariable/@ParentNodeId
	                  |se:UANodeSet/se:UAVariable/@DataType
	                  |se:UANodeSet/se:UAVariable/@DataType
	                 |se:UANodeSet/se:UAVariable/se:References/se:Reference
	                  |se:UANodeSet/se:UAVariable/se:References/se:Reference/@ReferenceType
	                  |se:UANodeSet/se:UAVariable/se:References/se:Reference/@IsForward
	                  |se:UANodeSet/se:UAVariable/se:Value/ss:String
	                  |se:UANodeSet/se:UAVariable/se:DisplayName  
	                  |se:UANodeSet/se:UAObjectType/@NodeId
	                  |se:UANodeSet/se:UAObjectType/@BrowseName
	                  |se:UANodeSet/se:UAObjectType/se:References/se:Reference/@ReferenceType
	                  |se:UANodeSet/se:UAObjectType/se:References/se:Reference/@IsForward
	                  |se:UANodeSet/se:UAObjectType/se:References/se:Reference"
	                  mode="krextor:main">
 <xsl:apply-templates select="." mode="krextor:add-literal-property"/>
</xsl:template>

<xsl:template match="//se:Value/@String" mode="krextor:main">
  <xsl:call-template name="krextor:add-literal-property">
    <xsl:with-param name="property" select="'&opcua;hasString'"/>
    <xsl:with-param name="datatype " select="'&xsd;string'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="//se:RefSemantic/@String" mode="krextor:main">
  <xsl:call-template name="krextor:add-literal-property">
    <xsl:with-param name="property" select="'&opcua;hasString'"/>
    <xsl:with-param name="datatype " select="'&xsd;string'"/>
  </xsl:call-template>
</xsl:template>

</xsl:transform>
