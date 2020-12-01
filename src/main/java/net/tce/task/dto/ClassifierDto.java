package net.tce.task.dto;

import net.tce.dto.ComunDto;

public class ClassifierDto extends ComunDto{


	private String seedDir;
	private String crawlId;
	private String solrUrl;
	private Integer rounds;
	private String query;
	private String coreName;


	public String getSeedDir() {
		return seedDir;
	}
	public void setSeedDir(String seedDir) {
		this.seedDir = seedDir;
	}
	public String getCrawlId() {
		return crawlId;
	}
	public void setCrawlId(String crawlId) {
		this.crawlId = crawlId;
	}
	public String getSolrUrl() {
		return solrUrl;
	}
	public void setSolrUrl(String solrUrl) {
		this.solrUrl = solrUrl;
	}
	public Integer getRounds() {
		return rounds;
	}
	public void setRounds(Integer rounds) {
		this.rounds = rounds;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}	
	public String getCoreName() {
		return coreName;
	}
	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}
}