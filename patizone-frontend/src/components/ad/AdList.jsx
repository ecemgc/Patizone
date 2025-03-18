import React from "react";
import Spinner from "../Spinner";
import Ad from "./Ad";
import Grid from "@mui/material/Grid2";
import { Box } from "@mui/material";
import { styled } from "@mui/material/styles";
import Paper from "@mui/material/Paper";

function AdList({ adList }) {
  const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: "#fff",
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: "center",
    color: theme.palette.text.secondary,
    ...theme.applyStyles("dark", {
      backgroundColor: "#1A2027",
    }),
  }));

  return (
    <Spinner active={!adList}>
      <Box sx={{ flexGrow: 1 }}>
        <Grid container spacing={2}>
          {adList.map((ad) => (
            <Grid size={4}>
              <Ad ad={ad} />
            </Grid>
          ))}
        </Grid>
      </Box>
    </Spinner>
  );
}

export default AdList;
