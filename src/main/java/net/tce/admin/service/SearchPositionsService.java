package net.tce.admin.service;

import java.util.List;

import net.tce.dto.PosicionDto;

public interface SearchPositionsService {

	boolean  searchPositions(List<PosicionDto> lsPosicionDto);
}
