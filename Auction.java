import java.util.ArrayList;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael Kölling.
 * @version 2011.07.31
 */
public class Auction
{
    // The list of Lots in this auction.
    private ArrayList<Lot> lots;
    // The number that will be given to the next lot entered
    // into this auction.
    private int nextLotNumber;

    /**
     * Create a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
    }

    /**
     * Enter a new lot into the auction.
     * @param description A description of the lot.
     */
    public void enterLot(String description)
    {
        lots.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots()
    {
        for(Lot lot : lots) {
            System.out.println(lot.toString());
        }
    }

    /**
     * Make a bid for a lot.
     * A message is printed indicating whether the bid is
     * successful or not.
     * 
     * @param lotNumber The lot being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     */
    public void makeABid(int lotNumber, Person bidder, long value)
    {
        Lot selectedLot = getLot(lotNumber);
        if(selectedLot != null) {
            boolean successful = selectedLot.bidFor(new Bid(bidder,value));
            if(successful) {
                System.out.println("The bid for lot number " +
                    lotNumber + " was successful.");
            }
            else {
                // Report which bid is higher.
                Bid highestBid = selectedLot.getHighestBid();
                System.out.println("Lot number: " + lotNumber +
                    " already has a bid of: " +
                    highestBid.getValue());
            }
        }
    }

    /**
     * Return the lot with the given number. Return null
     * if a lot with this number does not exist.
     * @param lotNumber The number of the lot to return.
     */
    public Lot getLot(int lotNumber)
    {
        int i = 0;
        boolean encontrado = false;
        Lot loteADevolver = null;

        if ((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            while (i < lots.size() && !encontrado){
                Lot loteActual = lots.get(i);
                if (loteActual.getNumber() == lotNumber){
                    encontrado = true;
                    loteADevolver = loteActual;
                }
                i++;
            }
        }
        else {
            System.out.println("Lot number: " + lotNumber +
                " does not exist.");
        }
        return loteADevolver;
    }

    /**
     * Metodo para imprimir por pantalla detalles sobre los lotes en subasta.
     * Muestra informacion en funcion de si existe puja o no por dicho lote.
     */
    public void close(){
        for (Lot lote : lots){
            Bid mayorPuja = lote.getHighestBid();
            String detallesOriginales = lote.toString();
            if (mayorPuja != null){
                System.out.println(detallesOriginales + " Persona que lidera la puja: " + lote.getHighestBid().getBidder().getName());
            }
            else{
                System.out.println(detallesOriginales + " Nadie ha pujado por este lote todavia");
            }
        }
    }

    /**
     * Devuelve un ArrayList de los objetos Lot que no tengan una puja actualmente.
     */
    public ArrayList<Lot> getUnsold(){
        ArrayList<Lot> lotesNoVendidos = new ArrayList<Lot>();
        for (Lot lote : lots){
            if(lote.getHighestBid() == null){
                lotesNoVendidos.add(lote);
            }
        }
        return lotesNoVendidos;
    }
    
    /**
     * Eliminar el lote con el numero de lote especificado.
     * @param number El numero del lote que hay que eliminar
     * @return El lote con el numero dado o null si no existe tal lote.
     */
    public Lot removeLot(int number){
        Lot loteEliminado = getLot(number);
        lots.remove(loteEliminado);
        return loteEliminado;
    }
}
