package dk.dbc.automarc.dto;

import dk.dbc.marc.binding.DataField;
import dk.dbc.marc.binding.MarcRecord;

import java.util.ArrayList;
import java.util.List;

public class InfoMediaToMarc {

    public MarcRecord toMarc(InfoMediaDTO dto) {
        MarcRecord record = new MarcRecord();

        List<DataField> fields = new ArrayList<>();

        return record;
    }

}
